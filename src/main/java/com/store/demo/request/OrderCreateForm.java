package com.store.demo.request;

import com.store.demo.request.sms.CartItemIdForm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderCreateForm {

    @NotNull
    private Integer customerAddressId;
    @NotNull
    private Integer paymentForm;

    private String remarks;

    public Integer getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(Integer customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public Integer getPaymentForm() {
        return paymentForm;
    }

    public void setPaymentForm(Integer paymentForm) {
        this.paymentForm = paymentForm;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}