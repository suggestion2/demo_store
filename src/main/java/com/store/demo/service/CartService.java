package com.store.demo.service;

import com.store.demo.domain.Cart;
import com.store.demo.domain.CartItem;
import com.store.demo.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface CartService {
    Cart getById(Integer id);

    Cart select(Map<String, Object> map);

    List<Cart> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    void create(Cart cart,CartItem cartItem);

    Cart update(Cart cart, CartItem cartItem);

    int deleteById(Integer id);

    Cart getCurrentCart();

    Cart update(Cart cart);

}