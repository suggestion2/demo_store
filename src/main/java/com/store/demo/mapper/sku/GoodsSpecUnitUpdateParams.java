package com.store.demo.mapper.sku;


import com.store.demo.request.SpecUnitEditForm;

import java.util.List;

public class GoodsSpecUnitUpdateParams {
    private Integer goodsId;
    private Integer updateBy;
    private List<SpecUnitEditForm> list;

    public GoodsSpecUnitUpdateParams(Integer goodsId, List<SpecUnitEditForm> list, Integer updateBy) {
        this.goodsId = goodsId;
        this.updateBy = updateBy;
        this.list = list;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public List<SpecUnitEditForm> getList() {
        return list;
    }

    public void setList(List<SpecUnitEditForm> list) {
        this.list = list;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
}
