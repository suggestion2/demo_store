package com.store.demo.mapper;

import com.store.demo.domain.GoodsSpecUnit;

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

    int deleteById(Integer id);
}