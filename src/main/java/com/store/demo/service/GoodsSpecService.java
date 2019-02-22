package com.store.demo.service;

import com.store.demo.domain.GoodsSpec;
import com.store.demo.mapper.sku.GoodsSpecUpdateParams;
import com.store.demo.request.SpecEditForm;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface GoodsSpecService {
    GoodsSpec getById(Integer id);

    GoodsSpec select(Map<String, Object> map);

    List<GoodsSpec> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(GoodsSpec goodsSpec);

    int update(GoodsSpec goodsSpec);

    int deleteById(Integer id);

    int batchCreate(List<SpecEditForm> specEditFormList);
}