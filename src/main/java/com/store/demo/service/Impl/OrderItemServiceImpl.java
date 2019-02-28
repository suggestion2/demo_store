package com.store.demo.service.Impl;

import com.store.demo.domain.OrderItem;
import com.store.demo.response.OrderItemView;
import com.store.demo.service.OrderItemService;
import com.store.demo.mapper.OrderItemMapper;
import com.store.demo.service.oss.ImageConstants;
import com.store.demo.service.oss.OssService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OssService ossService;

    @Override
    public OrderItem getById(Integer id){
        return orderItemMapper.selectById(id);
    }
    @Override
    public OrderItem select(Map<String, Object> map){
        return orderItemMapper.select(map);
    }

    @Override
    public List<OrderItem> selectList(Map<String, Object> map){
        return orderItemMapper.selectList(map);
    }

    @Override
    public int selectUncommentCount(Integer orderId) {
        Map<String,Object> query = new HashMap<>();
        query.put("orderId",orderId);
        query.put("comment",0);
        return orderItemMapper.selectCount(query);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return orderItemMapper.selectCount(map);
    }

    @Override
    public int create(OrderItem orderItem){
        return orderItemMapper.insert(orderItem);
    }

    @Override
    public int update(OrderItem orderItem){
        return orderItemMapper.update(orderItem);
    }

    public int comment(OrderItem orderItem){
        return orderItemMapper.comment(orderItem);
    }

    @Override
    public int deleteById(Integer id){
        return orderItemMapper.deleteById(id);
    }

    @Override
    public List<OrderItemView> getViewListByOrderId(Integer orderId) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("bucket",ossService.getBucket(ImageConstants.GOODS));
        return orderItemMapper.selectShortList(map);
    }
}