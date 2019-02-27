package com.store.demo.response;

public class OrderCreateView {
    private String orderNumber;

    private String paymentNumber;

    public OrderCreateView(String orderNumber, String paymentNumber) {
        this.orderNumber = orderNumber;
        this.paymentNumber = paymentNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }
}
