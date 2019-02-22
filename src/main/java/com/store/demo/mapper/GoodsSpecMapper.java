package com.store.demo.mapper;

import com.store.demo.domain.GoodsSpec;
import com.store.demo.mapper.sku.GoodsSpecUpdateParams;
import com.store.demo.request.SpecEditForm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GoodsSpecMapper {

    GoodsSpec selectById(Integer id);

    GoodsSpec select(Map<String, Object> map);

    List<GoodsSpec> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(GoodsSpec goodsSpec);

    int update(GoodsSpec goodsSpec);

    int deleteById(Integer id);

    int batchInsert(List<SpecEditForm> specEditFormList);
}