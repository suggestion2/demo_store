package com.store.demo.context;



import com.store.demo.domain.Cart;
import com.store.demo.domain.Customer;
import com.store.demo.domain.User;
import com.store.demo.domain.Visitor;
import com.store.demo.service.CartService;
import com.store.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class SessionContext {
    private static final long CAPTCHA_EXPIRED_TIME = 120000L;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CartService cartService;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private CustomerService customerService;

    public void setUser(User user){
        httpSession.setAttribute("user",user);
    }

    /**
     * session visitor
     * */
    public void setVisitor(Visitor visitor){
        httpSession.setAttribute("visitor",visitor);
    }

    public User getUser(){
        return httpSession.getAttribute("user") == null ? null : (User)httpSession.getAttribute("user");
    }
    public void setCustomer(Customer customer){
        httpSession.setAttribute("customer",customer);
    }

    public Customer getCustomer(){
        return Objects.isNull(httpSession.getAttribute("customerId")) ? null : (Customer) httpSession.getAttribute("customerId");
    }

    public void logout(){
        httpSession.invalidate();
    }

    public Cart getCart(){
        //获得当前cartId
        Integer cartId = this.getCartId();
        //先判断该cartId是否存在。不存在返回空
        if(Objects.isNull(cartId)){
            return null;
        }
        //缓存没有判断数据库有没有该购物车
        Cart cart = cartService.getById(cartId);
        if(Objects.nonNull(cart)){
            return cart;
        }
        return cart;
    }

    /**
     * session cart
     * */
    public void setCartId(Integer id){
        httpSession.setAttribute("cartId",id);
    }

    private Integer getCartId(){
        //判断当前session有没有cartId
        if(Objects.nonNull(httpSession.getAttribute("cartId"))){
            return (int)httpSession.getAttribute("cartId");
        }
        Customer customer = this.getCustomer();
        //判断当前customer有没有cartId
        if(Objects.nonNull(customer) && Objects.nonNull(customer.getCartId())){
            httpSession.setAttribute("cartId",customer.getCartId());
            return customer.getCartId();
        }
        Visitor visitor = this.getVisitor();
        //判断当前visitor有没有cartId，游客有cartId，就把游客的cartId拿出来用
        if(Objects.nonNull(visitor.getCartId())){
            httpSession.setAttribute("cartId",visitor.getCartId());
            return visitor.getCartId();
        }
        return null;
    }

    public Visitor getVisitor(){
        return httpSession.getAttribute("visitor") == null ? null : (Visitor)httpSession.getAttribute("visitor");
    }


}
