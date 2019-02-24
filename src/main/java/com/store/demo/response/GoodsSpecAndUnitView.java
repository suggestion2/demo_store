package com.store.demo.response;

import com.store.demo.domain.Goods;
import com.store.demo.domain.GoodsSpec;
import com.store.demo.domain.GoodsSpecUnit;

import java.util.List;

/**
 * @author 吴
 * @create 2019/2/24 - 23:45
 */
public class GoodsSpecAndUnitView {
    private Goods goods;
    private List<SpecView> specViewList;
    private List<GoodsSpecUnit> unitList;

    public GoodsSpecAndUnitView(Goods goods, List<SpecView> specViewList, List<GoodsSpecUnit> unitList) {
        this.goods = goods;
        this.specViewList = specViewList;
        this.unitList = unitList;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public List<SpecView> getSpecViewList() {
        return specViewList;
    }

    public void setSpecViewList(List<SpecView> specViewList) {
        this.specViewList = specViewList;
    }

    public List<GoodsSpecUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<GoodsSpecUnit> unitList) {
        this.unitList = unitList;
    }
}
