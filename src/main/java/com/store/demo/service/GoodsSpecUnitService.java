package com.store.demo.service;

import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.mapper.params.GoodsStockUpdateParams;
import com.store.demo.mapper.sku.GoodsSpecUnitUpdateParams;
import com.store.demo.request.SpecUnitEditForm;
import com.store.demo.service.stock.GoodsStocks;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface GoodsSpecUnitService {
    GoodsSpecUnit getById(Integer id);

    GoodsSpecUnit select(Map<String, Object> map);

    List<GoodsSpecUnit> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(GoodsSpecUnit goodsSpecUnit);

    int update(GoodsSpecUnit goodsSpecUnit);

    int deleteByGoodsId(Integer id);

    int batchCreate(List<SpecUnitEditForm> list);

    List<GoodsSpecUnit> selectListByGoodsId(Integer goodsId);

    int batchUpdate(GoodsSpecUnitUpdateParams params);


    GoodsStocks getStocks(Integer unitId);

    int updateStocks(List<GoodsStockUpdateParams>  list);

}