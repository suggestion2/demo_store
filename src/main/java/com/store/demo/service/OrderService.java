package com.store.demo.service;

import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
    Order getById(Integer id);

    Order select(Map<String, Object> map);

    List<Order> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(Order order, List<OrderItem> list);

    int update(Order order);

    int deleteById(Integer id);
}