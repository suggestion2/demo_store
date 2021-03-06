package com.store.demo.context;



import com.store.demo.domain.*;
import com.store.demo.service.CartService;
import com.store.demo.service.CustomerService;
import com.store.demo.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Component
public class SessionContext {
    private static final long CAPTCHA_EXPIRED_TIME = 120000L;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VisitorService visitorService;

    public void login(Customer customer){
        this.setCustomer(customer);
        Visitor visitor = this.getVisitor();
        visitor.setCustomerId(customer.getId());
        visitorService.update(visitor);
        this.setVisitor(visitor);
    }
    public void setOrderType(int type){
        httpSession.setAttribute("orderType",type);
    }

    public Integer getOrderType(){
        return (Integer)httpSession.getAttribute("orderType");
    }

    public void removeOrderType(){
        httpSession.removeAttribute("orderType");
    }
    /**
     * 设置当前订单商品
     */
    public void setCurrentOrderItem(List<OrderItem> list){
        httpSession.setAttribute("orderItem",list);
    }
    /**
     *
     * @return得到当前订单
     */
    public List<OrderItem> getCurrentOrderItem(){
        return (List<OrderItem>) httpSession.getAttribute("orderItem");
    }
    /**
     *
     * 清空当前订单
     */
    public void removeCurrentOrderItem(){
        httpSession.removeAttribute("orderItem");
    }
    public void invalidate(){
        httpSession.invalidate();
    }

    public String getRemoteAddress(){
        return Objects.isNull(httpSession.getAttribute("ip")) ? null
                : (String) httpSession.getAttribute("ip");
    }
    public void setRemoteAddress(String ip){
        httpSession.setAttribute("ip",ip);
    }

    /**
     * session captcha
     * */
    public void setCaptcha(String phone,String captcha,Integer type){
        httpSession.setAttribute("captchaPhone",phone);
        httpSession.setAttribute("captcha",captcha);
        httpSession.setAttribute("captchaType",type);
        httpSession.setAttribute("captchaTime",System.currentTimeMillis());
    }
    public boolean validCaptcha(String phone,String captcha,Integer type){
        return Objects.equals(httpSession.getAttribute("captchaPhone"),phone)
                && Objects.equals(httpSession.getAttribute("captcha"),captcha)
                && Objects.equals(httpSession.getAttribute("captchaType"),type)
                && (long)httpSession.getAttribute("captchaTime") + CAPTCHA_EXPIRED_TIME > System.currentTimeMillis();
    }
    public void removeCaptcha(){
        httpSession.removeAttribute("captchaPhone");
        httpSession.removeAttribute("captcha");
        httpSession.removeAttribute("captchaTime");
        httpSession.removeAttribute("captchaType");
    }

    public void setUser(User user){
        httpSession.setAttribute("user",user);
    }

    /**
     * session visitor
     * */
    public void setVisitor(Visitor visitor){
        httpSession.setAttribute("visitor",visitor);
    }

    public Visitor getVisitor(){
        return httpSession.getAttribute("visitor") == null ? null : (Visitor)httpSession.getAttribute("visitor");
    }

    public User getUser(){
        return httpSession.getAttribute("user") == null ? null : (User)httpSession.getAttribute("user");
    }
    public void setCustomer(Customer customer){
        httpSession.setAttribute("customer",customer);
    }

    public Customer getCustomer(){
        Customer customer;
        if(Objects.isNull(httpSession.getAttribute("customer"))){
            if(Objects.isNull(this.getVisitor().getCustomerId())){
                return null;
            }
            customer = customerService.getById(this.getVisitor().getCustomerId());
            this.setCustomer(customer);
            return customer;
        }else {
            return (Customer)httpSession.getAttribute("customer");
        }
    }

    public void logout(){
        Visitor visitor = this.getVisitor();
        visitor.setCustomerId(null);
        visitorService.update(visitor);
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
    public void setCartId(Customer customer,Integer id){
        if(Objects.isNull(customer)){
            httpSession.setAttribute("visitorCartId",id);
        }
        if(Objects.nonNull(customer)){
            httpSession.setAttribute("customerCartId",id);
        }
    }

    private Integer getCartId(){
        Customer customer = this.getCustomer();
        //判断当前session有没有cartId
        if(Objects.nonNull(customer)&&Objects.nonNull(httpSession.getAttribute("customerCartId"))){
            return (int)httpSession.getAttribute("customerCartId");
        }
        if(Objects.isNull(customer)&&Objects.nonNull(httpSession.getAttribute("visitorCartId"))){
            return (int)httpSession.getAttribute("visitorCartId");
        }
        //判断当前customer有没有cartId
        if(Objects.nonNull(customer) && Objects.nonNull(customer.getCartId())){
            httpSession.setAttribute("customerCartId",customer.getCartId());
            return customer.getCartId();
        }
        Visitor visitor = this.getVisitor();
        //会员没登入的时候，判断当前visitor有没有cartId，游客有cartId，就把游客的cartId拿出来用，
        if(Objects.isNull(customer)&&Objects.nonNull(visitor.getCartId())){
            httpSession.setAttribute("visitorCartId",visitor.getCartId());
            return visitor.getCartId();
        }
        return null;
    }


}
