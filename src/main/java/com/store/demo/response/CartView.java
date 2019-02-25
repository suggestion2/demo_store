package com.store.demo.response;

import com.store.demo.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;

public class CartView {

    private Double amount;
    private Integer count;

    @Autowired
    public CartView() {
    }

    public CartView(Cart cart) {
        this.amount = cart.getAmount().doubleValue();
        this.count = cart.getCount();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
