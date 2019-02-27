package com.store.demo.response;

import com.store.demo.domain.OrderItem;

import java.util.List;

public class OrderPrepareView {


    private Double goodsAmount;
    private Double shippingCostAmount;
    private Double totalAmount;
    private Integer count;

    private List<OrderItem> list;

    public Double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Double getShippingCostAmount() {
        return shippingCostAmount;
    }

    public void setShippingCostAmount(Double shippingCostAmount) {
        this.shippingCostAmount = shippingCostAmount;
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

    public List<OrderItem> getList() {
        return list;
    }

    public void setList(List<OrderItem> list) {
        this.list = list;
    }


}
