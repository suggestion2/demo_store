package com.store.demo.service;

import com.store.demo.domain.Goods;
import com.store.demo.mapper.params.GoodsStockUpdateParams;
import com.store.demo.service.stock.GoodsStocks;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface GoodsService {
    Goods getById(Integer id);

    Goods select(Map<String, Object> map);

    List<Goods> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(Goods goods);

    int update(Goods goods);

    int deleteById(Integer id);

    Goods resetStatus(Goods goods);

    Map<String,Integer> getStocks(List<GoodsStocks> checkList);



}