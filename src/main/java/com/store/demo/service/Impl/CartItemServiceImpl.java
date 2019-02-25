package com.store.demo.service.Impl;

import com.store.demo.domain.CartItem;
import com.store.demo.service.CartItemService;
import com.store.demo.mapper.CartItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public CartItem getById(Integer id){
        return cartItemMapper.selectById(id);
    }
    @Override
    public CartItem select(Map<String, Object> map){
        return cartItemMapper.select(map);
    }

    @Override
    public List<CartItem> selectList(Map<String, Object> map){
        return cartItemMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return cartItemMapper.selectCount(map);
    }

    @Override
    public int create(CartItem cartItem){
        return cartItemMapper.insert(cartItem);
    }

    @Override
    public int update(CartItem cartItem){
        return cartItemMapper.update(cartItem);
    }

    @Override
    public int deleteById(Integer id){
        return cartItemMapper.deleteById(id);
    }
}