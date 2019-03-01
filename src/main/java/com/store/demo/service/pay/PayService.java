package com.store.demo.service.pay;

import com.store.demo.domain.Customer;
import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.domain.Payment;
import com.store.demo.response.OrderItemView;
import com.store.demo.service.*;
import com.store.demo.service.stock.GoodsStocks;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PayService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderItemService orderItemService;


    public OrderPayment payCheck(String number){
        Customer customer = customerService.getCurrentCustomer();
        Payment payment = paymentService.getByNumber(number);
        if(Objects.isNull(payment) || !payment.getCustomerId().equals(customer.getId())){
            throw new ResourceNotFoundException("payment not found");
        }

        Order order = orderService.getById(payment.getOrderId());
        if(Objects.isNull(order)){
            throw new ResourceNotFoundException("order not found");
        }

        List<OrderItemView> orderItemList = orderItemService.getListByOrderId(order.getId());

        List<GoodsStocks> stocksCheckList = new ArrayList<>();
        orderItemList.forEach(o->{
            GoodsStocks goodsStocks = new GoodsStocks();
            goodsStocks.setId(o.getGoodsId());
            goodsStocks.setUnitId(o.getUnitId());
            goodsStocks.setStocks(o.getCount());
            stocksCheckList.add(goodsStocks);
        });

        Map<String,Integer> stocksMap = goodsService.getStocks(stocksCheckList);
        orderItemList.forEach(o->{
            if(o.getCount() > stocksMap.get(o.getGoodsId() + ":" + o.getUnitId())) {
                throw new InvalidRequestException("订单商品库存不足,请重新下单");
            }
        });
        return new OrderPayment(order,payment,orderItemList);
    }
}
