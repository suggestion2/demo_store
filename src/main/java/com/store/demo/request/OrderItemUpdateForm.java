package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderItemUpdateForm {

    @NotNull
    private Integer id;
    @NotNull
    private Integer orderId;
    @NotNull
    private Integer cartItemId;
    @NotNull
    private Integer goodsId;
    @NotNull
    private Integer unitId;
    @NotEmpty
    private String goodsName;
    @NotEmpty
    private String bannerUrl;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigDecimal shippingCost;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Integer count;
    @NotEmpty
    private String unitName;
    @NotNull
    private Integer comment;

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
    public Integer getCartItemId() {
    return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
    this.cartItemId = cartItemId;
    }
    public Integer getGoodsId() {
    return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
    }
    public Integer getUnitId() {
    return unitId;
    }

    public void setUnitId(Integer unitId) {
    this.unitId = unitId;
    }
    public String getGoodsName() {
    return goodsName;
    }

    public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
    }
    public String getBannerUrl() {
    return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
    this.bannerUrl = bannerUrl;
    }
    public BigDecimal getPrice() {
    return price;
    }

    public void setPrice(BigDecimal price) {
    this.price = price;
    }
    public BigDecimal getShippingCost() {
    return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
    this.shippingCost = shippingCost;
    }
    public BigDecimal getAmount() {
    return amount;
    }

    public void setAmount(BigDecimal amount) {
    this.amount = amount;
    }
    public Integer getCount() {
    return count;
    }

    public void setCount(Integer count) {
    this.count = count;
    }
    public String getUnitName() {
    return unitName;
    }

    public void setUnitName(String unitName) {
    this.unitName = unitName;
    }
    public Integer getComment() {
    return comment;
    }

    public void setComment(Integer comment) {
    this.comment = comment;
    }

}