package com.store.demo.response;

import org.springframework.beans.factory.annotation.Autowired;

public class CartEditView {
    private CartView cartView;
    private CartItemView cartItem;

    private Integer maxCount;
    private Integer outOfStock;

    @Autowired
    public CartEditView() {
    }

    public CartEditView(CartView cartView, CartItemView cartItem, Integer maxCount, Integer outOfStock) {
        this.cartView = cartView;
        this.cartItem = cartItem;
        this.maxCount = maxCount;
        this.outOfStock = outOfStock;
    }

    public CartEditView(CartView cartView, CartItemView cartItem) {
        this(cartView,cartItem,0,0);
    }

    public CartView getCartView() {
        return cartView;
    }

    public void setCartView(CartView cartView) {
        this.cartView = cartView;
    }

    public CartItemView getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItemView cartItem) {
        this.cartItem = cartItem;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(Integer outOfStock) {
        this.outOfStock = outOfStock;
    }
}
