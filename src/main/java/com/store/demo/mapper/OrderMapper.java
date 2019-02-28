package com.store.demo.mapper;

import com.store.demo.domain.Order;
import com.store.demo.response.OrderItemView;
import com.store.demo.response.OrderShortView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderMapper {

    Order selectById(Integer id);

    Order select(Map<String, Object> map);

    List<Order> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    List<OrderShortView> selectViewList(Map<String, Object> map);

    int insert(Order order);

    int update(Order order);

    int comment(Order order);

    int deleteById(Integer id);

    int cancel(Order order);

}