package com.store.demo.response;

import org.springframework.beans.factory.annotation.Autowired;

public class CartDetailView {
    private CartView cartView;

    private Integer change;

    private CartItemListView validList;

    private CartItemListView discontinuedList;

    @Autowired
    public CartDetailView() {
    }

    public CartDetailView(Integer change, CartView cartView, CartItemListView validList, CartItemListView discontinuedList) {
        this.change = change;
        this.cartView = cartView;
        this.validList = validList;
        this.discontinuedList = discontinuedList;
    }

    public CartView getCartView() {
        return cartView;
    }

    public void setCartView(CartView cartView) {
        this.cartView = cartView;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public CartItemListView getValidList() {
        return validList;
    }

    public void setValidList(CartItemListView validList) {
        this.validList = validList;
    }

    public CartItemListView getDiscontinuedList() {
        return discontinuedList;
    }

    public void setDiscontinuedList(CartItemListView discontinuedList) {
        this.discontinuedList = discontinuedList;
    }
}
