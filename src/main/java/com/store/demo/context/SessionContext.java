package com.store.demo.context;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class SessionContext {
    private static final long CAPTCHA_EXPIRED_TIME = 120000L;

    @Autowired
    private HttpSession httpSession;

    /*public void setUser(User user){
        httpSession.setAttribute("user",user);
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
    }*/
}
