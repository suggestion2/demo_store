package com.store.demo.mapper.sku;

import com.store.demo.request.SpecEditForm;

import java.util.List;

public class GoodsSpecUpdateParams {
    private Integer goodsId;
    private Integer updateBy;
    private List<SpecEditForm> list;

    public GoodsSpecUpdateParams(Integer goodsId, List<SpecEditForm> list, Integer updateBy) {
        this.goodsId = goodsId;
        this.list = list;
        this.updateBy = updateBy;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public List<SpecEditForm> getList() {
        return list;
    }

    public void setList(List<SpecEditForm> list) {
        this.list = list;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
}
