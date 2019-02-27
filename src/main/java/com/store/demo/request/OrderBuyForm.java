package com.store.demo.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderBuyForm {
    @NotNull
    private Integer goodsId;
    @Min(value = 1,message = "至少添加1个商品")
    private Integer count;
    @Min(value = 1,message = "至少添加1个sku")
    private Integer unitId;


    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
