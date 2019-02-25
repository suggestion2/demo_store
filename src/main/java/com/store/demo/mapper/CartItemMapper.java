package com.store.demo.mapper;

import com.store.demo.domain.CartItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartItemMapper {

    CartItem selectById(Integer id);

    CartItem select(Map<String, Object> map);

    List<CartItem> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(CartItem cartItem);

    int update(CartItem cartItem);

    int deleteById(Integer id);
}