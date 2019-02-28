package com.store.demo.service;

import com.store.demo.domain.OrderItem;
import com.store.demo.response.OrderItemView;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface OrderItemService {
    OrderItem getById(Integer id);

    OrderItem select(Map<String, Object> map);

    List<OrderItem> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(OrderItem orderItem);

    int update(OrderItem orderItem);

    int deleteById(Integer id);

    List<OrderItemView> getViewListByOrderId(Integer orderId);

}