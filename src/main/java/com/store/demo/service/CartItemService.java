package com.store.demo.service;

import com.store.demo.domain.CartItem;
import com.store.demo.response.CartItemView;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface CartItemService {
    CartItem getById(Integer id);

    CartItem select(Map<String, Object> map);

    List<CartItem> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(CartItem cartItem);

    int update(CartItem cartItem);

    int deleteById(Integer id);

    CartItem getByGoodsId(Integer goodsId,Integer cartId);

    CartItem getByUnitId(Integer unitId, Integer cartId);

    int discontinuedById(Integer id);

    List<CartItemView> getShortListByCartId(Integer cartId, Integer validType);

}