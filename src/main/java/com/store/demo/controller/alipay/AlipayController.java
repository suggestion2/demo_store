package com.store.demo.controller.alipay;

import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.response.alipay.AlipayNativePayView;
import com.store.demo.service.*;
import com.store.demo.service.alipay.AlipayPageOrderFormString;
import com.store.demo.service.alipay.AlipayService;
import com.store.demo.service.pay.OrderPayment;
import com.store.demo.service.pay.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



@RestController("CommonAlipayController")
@RequestMapping(value = "/api/alipay")
public class AlipayController {

    private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private PayService payService;

    @Autowired
    private PaymentService paymentService;


    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @Transactional
    @CustomerLoginRequired
    public String notify(HttpServletRequest request){
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //验证签名
        boolean flag = alipayService.checkSign(params);

        if (!flag) {
            throw new RuntimeException("alipay sign fail");
        }
        //商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
        String paymentNumber = params.get("out_trade_no");
        String transactionId = params.get("trade_no");

        paymentService.payNotify(paymentNumber,transactionId);

        return "success";
    }

    @RequestMapping(value = "/page/pay/{number}", method = RequestMethod.GET)
    @CustomerLoginRequired
    public AlipayNativePayView pagePay(@PathVariable String number) {
        //检查库存是否满足
        OrderPayment orderPayment = payService.payCheck(number);
        //进行传参数生成form也就是url然后根据url重定向到支付宝用户登入界面和传参给支付宝
        AlipayPageOrderFormString string = alipayService.getPageOrderFormString(orderPayment.getOrder(), orderPayment.getPayment());
        return new AlipayNativePayView(string.getFormString());
    }

}
