package com.store.demo.mapper;

import com.store.demo.domain.Payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PaymentMapper {

    Payment selectById(Integer id);

    Payment select(Map<String, Object> map);

    List<Payment> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(Payment payment);

    int update(Payment payment);

    int deleteById(Integer id);
}