package com.store.demo.mapper;

import com.store.demo.domain.GoodsCategory;
import com.store.demo.response.GoodsCategoryView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GoodsCategoryMapper {

    GoodsCategory selectById(Integer id);

    GoodsCategory select(Map<String, Object> map);

    List<GoodsCategory> selectList(Map<String, Object> map);

    List<GoodsCategoryView> selectViewList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(GoodsCategory goodsCategory);

    int update(GoodsCategory goodsCategory);

    int updateStatus(GoodsCategory goodsCategory);

    int deleteById(Integer id);

    int selectInvalidCountByIdList(List<Integer> list);

}