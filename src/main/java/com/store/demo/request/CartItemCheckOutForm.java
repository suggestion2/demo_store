package com.store.demo.request;

import com.store.demo.domain.CartItem;
import com.store.demo.request.sms.CartItemIdForm;

import java.util.List;

public class CartItemCheckOutForm {

    private List<CartItemIdForm> list;

    public List<CartItemIdForm> getList() {
        return list;
    }

    public void setList(List<CartItemIdForm> list) {
        this.list = list;
    }
}
