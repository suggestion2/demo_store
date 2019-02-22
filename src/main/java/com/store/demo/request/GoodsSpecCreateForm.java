package com.store.demo.request;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GoodsSpecCreateForm {

    @NotEmpty
    private String title;
    @NotNull
    private Integer parentId;
    @NotNull
    private Integer goodsId;
    @NotNull
    private Integer primary;

    public String getTitle() {
    return title;
    }

    public void setTitle(String title) {
    this.title = title;
    }
    public Integer getParentId() {
    return parentId;
    }

    public void setParentId(Integer parentId) {
    this.parentId = parentId;
    }
    public Integer getGoodsId() {
    return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
    this.goodsId = goodsId;
    }
    public Integer getPrimary() {
    return primary;
    }

    public void setPrimary(Integer primary) {
    this.primary = primary;
    }

}