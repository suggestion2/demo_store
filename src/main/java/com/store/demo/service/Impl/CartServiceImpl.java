package com.store.demo.service.Impl;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Cart;
import com.store.demo.domain.CartItem;
import com.store.demo.domain.Customer;
import com.store.demo.domain.Visitor;
import com.store.demo.service.CartItemService;
import com.store.demo.service.CartService;
import com.store.demo.mapper.CartMapper;
import com.store.demo.service.CustomerService;
import com.store.demo.service.VisitorService;
import com.sug.core.platform.exception.LoginRequiredException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CartItemService cartItemService;

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
    @Transactional
    public Cart update(Cart cart, CartItem cartItem){

//        try {
            cartMapper.update(cart);
            if(Objects.isNull(cartItem.getId())){
                cartItem.setCartId(cart.getId());
                cartItemService.create(cartItem);
            }else if(cartItem.getCount() > 0){
                cartItemService.update(cartItem);
            }else if(cartItem.getCount().equals(0)){
                cartItemService.deleteById(cartItem.getId());
            }else {
                cartItemService.discontinuedById(cartItem.getId());
            }
            return cart;
//        }
    }



    @Override
    @Transactional
    public void create(Cart cart,CartItem cartItem){
        int result = cartMapper.insert(cart);
        if (result == 0) {
            throw new InvalidRequestException("[购物车]服务器繁忙,请稍后再试");
        }
        //拿出数据库插入的生成的id
        cartItem.setCartId(cart.getId());
        result = cartItemService.create(cartItem);
        if (result == 0) {
            throw new InvalidRequestException("[购物车商品]服务器繁忙,请稍后再试");
        }
        setCartId(sessionContext.getCustomer(),cart.getId());
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
        sessionContext.setCartId(customer,cartId);
        boolean customerCartId = false;
        //  当前用户没有购物车就进行添加
        if(Objects.nonNull(customer) && (Objects.isNull(customer.getCartId()))){
            customer.setCartId(cartId);
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