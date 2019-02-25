package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PaymentUpdateForm {

    @NotNull
    private Integer id;
    @NotNull
    private Integer orderId;
    @NotEmpty
    private String number;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Integer customerId;
    @NotEmpty
    private String customerPhone;
    @NotNull
    private Integer type;

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }
    public Integer getOrderId() {
    return orderId;
    }

    public void setOrderId(Integer orderId) {
    this.orderId = orderId;
    }
    public String getNumber() {
    return number;
    }

    public void setNumber(String number) {
    this.number = number;
    }
    public BigDecimal getAmount() {
    return amount;
    }

    public void setAmount(BigDecimal amount) {
    this.amount = amount;
    }
    public Integer getCustomerId() {
    return customerId;
    }

    public void setCustomerId(Integer customerId) {
    this.customerId = customerId;
    }
    public String getCustomerPhone() {
    return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
    }
    public Integer getType() {
    return type;
    }

    public void setType(Integer type) {
    this.type = type;
    }

}