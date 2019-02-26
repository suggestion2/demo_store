package com.store.demo.mapper;

import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.mapper.sku.GoodsSpecUnitUpdateParams;
import com.store.demo.request.SpecUnitEditForm;
import com.store.demo.service.stock.GoodsStocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GoodsSpecUnitMapper {

    GoodsSpecUnit selectById(Integer id);

    GoodsSpecUnit select(Map<String, Object> map);

    List<GoodsSpecUnit> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(GoodsSpecUnit goodsSpecUnit);

    int update(GoodsSpecUnit goodsSpecUnit);

    int deleteByGoodsId(Integer id);

    int batchInsert(List<SpecUnitEditForm> list);

    int batchUpdate(GoodsSpecUnitUpdateParams params);

    GoodsStocks selectStockById(Integer id);

    List<GoodsStocks> selectStockList(List<Integer> list);



}