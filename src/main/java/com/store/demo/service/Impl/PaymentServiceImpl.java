package com.store.demo.service.Impl;

import com.store.demo.domain.Payment;
import com.store.demo.service.PaymentService;
import com.store.demo.mapper.PaymentMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.store.demo.constants.PaymentConstants.ORDER;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentMapper paymentMapper;

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
}