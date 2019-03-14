package com.store.demo.service.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.domain.Payment;
import com.store.demo.response.OrderItemView;
import com.store.demo.service.OrderItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AlipayService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayService.class);

    private AlipayClient alipayClient;

    @Value("${alipay.appId:@null}")
    private String appId;

    @Value("${alipay.app.privateKey:@null}")
    private String appPrivateKey;

    @Value("${alipay.ali.publicKey:@null}")
    private String aliPublicKey;

    @Value("${alipay.pay.notifyUrl:@null}")
    private String notifyUrl;

    @Value("${alipay.pay.page.returnUrl:@null}")
    private String returnUrl;

    @Autowired
    private OrderItemService orderItemService;

    @PostConstruct
    private void initClient(){
        this.alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, appPrivateKey, "json", "UTF-8", aliPublicKey, "RSA2");
    }

    public AlipayPageOrderFormString getPageOrderFormString(Order order, Payment payment){
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        List<OrderItemView> list = orderItemService.getViewListByOrderId(order.getId());

        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
        String bizContent = "{" +
                "\"out_trade_no\":\"" + payment.getNumber() + "\"," +
                "\"total_amount\":\"" + String.valueOf(payment.getAmount().doubleValue()) +  "\"," +
                "\"subject\":\"" + list.get(0).getGoodsName()
                + (order.getCount() > 1 ? "等" + order.getCount() + "件商品"  : "1件商品")+ "\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"" +
                "}";
        alipayRequest.setBizContent(bizContent);//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            logger.error("alipay wap fail : " + e.getErrMsg());
            throw new RuntimeException(e);
        }

        return new AlipayPageOrderFormString(form);
    }
    public AlipayWapOrderFormString getWapOrderFormString(Order order,Payment payment){
        AlipayTradeWapPayRequest  alipayRequest = new AlipayTradeWapPayRequest();
        List<OrderItemView> list = orderItemService.getViewListByOrderId(order.getId());

        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址
        String bizContent = "{" +
                "\"out_trade_no\":\"" + payment.getNumber() + "\"," +
                "\"total_amount\":\"" + String.valueOf(payment.getAmount().doubleValue()) +  "\"," +
                "\"subject\":\"" + list.get(0).getGoodsName()
                + (order.getCount() > 1 ? "等" + order.getCount() + "件商品"  : "1件商品")+ "\"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"" +
                "}";
        alipayRequest.setBizContent(bizContent);//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            logger.error("alipay wap fail : " + e.getErrMsg());
            throw new RuntimeException(e);
        }

        return new AlipayWapOrderFormString(form);
    }
    public boolean checkSign(Map<String,String> params){
        try {
            return AlipaySignature.rsaCheckV1(params, aliPublicKey, "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            logger.error("alipay check sign fail : " + e.getErrMsg());
            throw new RuntimeException(e);
        }
    }
}
