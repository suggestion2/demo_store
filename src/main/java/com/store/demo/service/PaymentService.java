package com.store.demo.service;

import com.store.demo.domain.Payment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface PaymentService {
    Payment getById(Integer id);

    Payment select(Map<String, Object> map);

    List<Payment> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(Payment payment);

    int update(Payment payment);

    int deleteById(Integer id);

    Payment getByOrderId(Integer id);

    Payment getByNumber(String number);

    void payNotify(String number,String transactionId);


}