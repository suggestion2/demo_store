package com.store.demo.mapper;

import com.store.demo.domain.OrderItem;
import com.store.demo.response.OrderItemView;
import com.store.demo.response.OrderView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderItemMapper {

    OrderItem selectById(Integer id);

    OrderItem select(Map<String, Object> map);

    List<OrderItemView > selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(OrderItem orderItem);

    int update(OrderItem orderItem);

    int comment(OrderItem orderItem);

    int deleteById(Integer id);

    int batchInsert(List<OrderItem> list);

    List<OrderItemView> selectShortList(Map<String, Object> map);

    int cancelByOrderId(Integer id);



}