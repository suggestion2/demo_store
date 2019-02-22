package com.store.demo.domain;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsSpecUnit {

    private Integer id;
    private String title;
    private Integer goodsId;
    private String specIds;
    private Integer stocks;
    private BigDecimal price;
    private Integer salesVolume;
    private Integer primary;
    private BigDecimal shippingCost;
    private Date createTime;
    private Date updateTime;
    private Integer createBy;
    private Integer updateBy;
    private Integer status;
    private Integer valid;
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
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }
    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}