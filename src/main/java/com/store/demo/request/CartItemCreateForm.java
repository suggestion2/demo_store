package com.store.demo.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemCreateForm {
    @NotNull
    private Integer id;
    @Min(value = 1,message = "至少添加1个商品")
    private Integer count;

    private Integer unitId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
