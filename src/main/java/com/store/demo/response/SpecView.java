package com.store.demo.response;

import com.store.demo.domain.GoodsSpec;

import java.util.List;

public class SpecView {
    private GoodsSpec goodsSpec;

    private List<GoodsSpec> list;

    public SpecView(GoodsSpec goodsSpec, List<GoodsSpec> list) {
        this.goodsSpec = goodsSpec;
        this.list = list;
    }

    public GoodsSpec getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(GoodsSpec goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public List<GoodsSpec> getList() {
        return list;
    }

    public void setList(List<GoodsSpec> list) {
        this.list = list;
    }
}
