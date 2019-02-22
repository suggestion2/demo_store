package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GoodsSpecUnitUpdateForm {

    @NotNull
    private Integer id;
    @NotEmpty
    private String title;
    @NotNull
    private Integer goodsId;
    @NotEmpty
    private String specIds;
    @NotNull
    private Integer stocks;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer salesVolume;
    @NotNull
    private Integer primary;
    @NotNull
    private BigDecimal shippingCost;
    @NotEmpty
    private String imageUrl;

    public Integer getId() {
    return id;
    }

    public void setId(Integer id) {
    this.id = id;
    }
    public String getTitle() {
    return title;
    }

    public void setTitle(String title) {
    this.title = title;
    }
    public Integer getGoodsId() {
    return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
    }
    public String getSpecIds() {
    return specIds;
    }

    public void setSpecIds(String specIds) {
    this.specIds = specIds;
    }
    public Integer getStocks() {
    return stocks;
    }

    public void setStocks(Integer stocks) {
    this.stocks = stocks;
    }
    public BigDecimal getPrice() {
    return price;
    }

    public void setPrice(BigDecimal price) {
    this.price = price;
    }
    public Integer getSalesVolume() {
    return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
    this.salesVolume = salesVolume;
    }
    public Integer getPrimary() {
    return primary;
    }

    public void setPrimary(Integer primary) {
    this.primary = primary;
    }
    public BigDecimal getShippingCost() {
    return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
    this.shippingCost = shippingCost;
    }
    public String getImageUrl() {
    return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    }

}