package com.store.demo.mapper;

import com.store.demo.domain.Goods;
import com.store.demo.mapper.params.GoodsStockUpdateParams;
import com.store.demo.service.stock.GoodsStocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GoodsMapper {

    Goods selectById(Integer id);

    Goods select(Map<String, Object> map);

    List<Goods> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(Goods goods);

    int update(Goods goods);

    int updateStatus(Goods goods);

    int deleteById(Integer id);


}