package com.store.demo.service.pay;


import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.domain.Payment;
import com.store.demo.response.OrderItemView;

import java.util.List;

public class OrderPayment {
    private Order order;
    private Payment payment;
    private List<OrderItemView> orderItemList;

    public OrderPayment(Order order, Payment payment, List<OrderItemView> orderItemList) {
        this.order = order;
        this.payment = payment;
        this.orderItemList = orderItemList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public List<OrderItemView> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemView> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
