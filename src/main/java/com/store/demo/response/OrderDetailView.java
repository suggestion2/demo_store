package com.store.demo.response;


import com.store.demo.domain.AfterSale;
import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.domain.Payment;

import java.util.List;

public class OrderDetailView {
    private OrderView orderView;

    private String paymentNumber;

    private List<OrderItemView> list;

    public OrderDetailView(OrderView orderView, List<OrderItemView> list,String paymentNumber) {
        this.orderView = orderView;
        this.list = list;
        this.paymentNumber = paymentNumber;
    }

    public OrderDetailView(OrderView orderView, List<OrderItemView> list) {
        this(orderView, list,null);
    }

    public OrderView getOrderView() {
        return orderView;
    }

    public void setOrderView(OrderView orderView) {
        this.orderView = orderView;
    }

    public List<OrderItemView> getList() {
        return list;
    }

    public void setList(List<OrderItemView> list) {
        this.list = list;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }
}
