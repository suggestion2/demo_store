package com.store.demo.service.Impl;

import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.mapper.OrderItemMapper;
import com.store.demo.service.OrderService;
import com.store.demo.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Order getById(Integer id){
        return orderMapper.selectById(id);
    }
    @Override
    public Order select(Map<String, Object> map){
        return orderMapper.select(map);
    }

    @Override
    public List<Order> selectList(Map<String, Object> map){
        return orderMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return orderMapper.selectCount(map);
    }

    @Override
    @Transactional
    public int create(Order order, List<OrderItem> list){
        orderMapper.insert(order);
        list.forEach(i->i.setOrderId(order.getId()));
        return orderItemMapper.batchInsert(list);
    }

    @Override
    public int update(Order order){
        return orderMapper.update(order);
    }

    @Override
    public int deleteById(Integer id){
        return orderMapper.deleteById(id);
    }
}