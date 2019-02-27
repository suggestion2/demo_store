package com.store.demo.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sug.core.util.jsonFormat.SimpleDateTimeSerializer;

import java.util.Date;
import java.util.List;

public class OrderShortView {

    private String number;
    private String customerAddress;
    private Double shippingCostAmount;
    private Double totalAmount;
    private Integer count;
    @JsonSerialize(using = SimpleDateTimeSerializer.class)
    private Date createTime;
    private Integer status;

    private List<OrderItemShortView> list;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getShippingCostAmount() {
        return shippingCostAmount;
    }

    public void setShippingCostAmount(Double shippingCostAmount) {
        this.shippingCostAmount = shippingCostAmount;
    }

    public List<OrderItemShortView> getList() {
        return list;
    }

    public void setList(List<OrderItemShortView> list) {
        this.list = list;
    }
}
