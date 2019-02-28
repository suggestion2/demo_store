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

    List<OrderItemView> selectList(Map<String, Object> map);

    int selectUncommentCount(Integer orderId);

    int selectCount(Map<String, Object> map);

    int create(OrderItem orderItem);

    int update(OrderItem orderItem);

    int comment(OrderItem orderItem);

    int deleteById(Integer id);

    List<OrderItemView> getViewListByOrderId(Integer orderId);

    List<OrderItemView > getListByOrderId(Integer id);


}