package com.store.demo.response;


import com.store.demo.domain.AfterSale;
import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.domain.Payment;

import java.util.List;

public class OrderDetailView {
    private Order order;

    private List<OrderItem> list;

    private Payment payment;

    private AfterSale afterSale;

    public OrderDetailView(Order order, List<OrderItem> list, Payment payment, AfterSale afterSale) {
        this.order = order;
        this.list = list;
        this.payment = payment;
        this.afterSale = afterSale;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public AfterSale getAfterSale() {
        return afterSale;
    }

    public void setAfterSale(AfterSale afterSale) {
        this.afterSale = afterSale;
    }
}
