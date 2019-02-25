package com.store.demo.service.Impl;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Cart;
import com.store.demo.domain.Customer;
import com.store.demo.domain.Visitor;
import com.store.demo.service.CartService;
import com.store.demo.mapper.CartMapper;
import com.store.demo.service.CustomerService;
import com.store.demo.service.VisitorService;
import com.sug.core.platform.exception.LoginRequiredException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VisitorService visitorService;

    @Override
    public Cart getById(Integer id){
        return cartMapper.selectById(id);
    }
    @Override
    public Cart select(Map<String, Object> map){
        return cartMapper.select(map);
    }

    @Override
    public List<Cart> selectList(Map<String, Object> map){
        return cartMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return cartMapper.selectCount(map);
    }

    @Override
    public int create(Cart cart){
        return cartMapper.insert(cart);
    }

    @Override
    public int update(Cart cart){
        return cartMapper.update(cart);
    }

    @Override
    public int deleteById(Integer id){
        return cartMapper.deleteById(id);
    }

    @Override
    public Cart getCurrentCart() {
        return sessionContext.getCart();
    }

    public void setCartId(Customer customer,Integer cartId){
        sessionContext.setCartId(cartId);
        boolean customerCartId = false;
        if(Objects.nonNull(customer) && (Objects.isNull(customer.getCartId()) || !customer.getCartId().equals(cartId))){
            customer.setCartId(cartId);
            //TODO customer完成再修改
            customerService.update(customer);
            customerCartId = true;
        }
        Visitor visitor = sessionContext.getVisitor();
        boolean changeCartId = false;
        if(customerCartId && Objects.nonNull(visitor.getCartId())){
            visitor.setCartId(null);
            changeCartId = true;
        }
        if(!customerCartId && (Objects.isNull(visitor.getCartId()) || !visitor.getCartId().equals(cartId))){
            visitor.setCartId(cartId);
            changeCartId = true;
        }
        if(changeCartId){
            visitorService.update(visitor);
            sessionContext.setVisitor(visitor);
        }
    }
}