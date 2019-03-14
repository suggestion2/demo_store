package com.store.demo.service.Impl;

import com.store.demo.constants.OrderConstants;
import com.store.demo.constants.PaymentConstants;
import com.store.demo.domain.*;
import com.store.demo.mapper.params.GoodsStockUpdateParams;
import com.store.demo.response.OrderItemView;
import com.store.demo.service.*;
import com.store.demo.mapper.PaymentMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.store.demo.constants.PaymentConstants.ORDER;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private GoodsSpecUnitService goodsSpecUnitService;

    @Autowired
    private CustomerService customerService;

    @Override
    public Payment getById(Integer id){
        return paymentMapper.selectById(id);
    }
    @Override
    public Payment select(Map<String, Object> map){
        return paymentMapper.select(map);
    }

    @Override
    public List<Payment> selectList(Map<String, Object> map){
        return paymentMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return paymentMapper.selectCount(map);
    }

    @Override
    public int create(Payment payment){
        return paymentMapper.insert(payment);
    }

    @Override
    public int update(Payment payment){
        return paymentMapper.update(payment);
    }

    @Override
    public int deleteById(Integer id){
        return paymentMapper.deleteById(id);
    }

    @Override
    public Payment getByOrderId(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId",id);
        map.put("type",ORDER);
        return paymentMapper.select(map);
    }

    @Override
    public Payment getByNumber(String number) {
        Map<String, Object> map = new HashMap<>();
        map.put("number",number);
        return paymentMapper.select(map);
    }

    @Override
    /**
     * 修改库存
     */
    public void payNotify(String number,String transactionId) {
        Payment payment = this.getByNumber(number);
        payment.setStatus(PaymentConstants.PAID);

        Order order = orderService.getById(payment.getOrderId());
        order.setStatus(OrderConstants.PAID);

        List<OrderItemView> orderItemList = orderItemService.getListByOrderId(order.getId());
        List<GoodsStockUpdateParams> stockUpdateParamsList = new ArrayList<>();
        orderItemList.forEach(o->{
            GoodsStockUpdateParams goodsStockUpdateParams = new GoodsStockUpdateParams();
            goodsStockUpdateParams.setGoodsId(o.getGoodsId());
            goodsStockUpdateParams.setUnitId(o.getUnitId());
            goodsStockUpdateParams.setCount(o.getCount());

            stockUpdateParamsList.add(goodsStockUpdateParams);
        });

        goodsSpecUnitService.updateStocks(stockUpdateParamsList);

        this.update(payment);
        orderService.update(order);
    }

}