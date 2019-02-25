package com.store.demo.mapper;

import com.store.demo.domain.Cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartMapper {

    Cart selectById(Integer id);

    Cart select(Map<String, Object> map);

    List<Cart> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(Cart cart);

    int update(Cart cart);

    int deleteById(Integer id);
}