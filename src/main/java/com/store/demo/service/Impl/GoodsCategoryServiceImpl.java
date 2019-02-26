package com.store.demo.service.Impl;

import com.store.demo.domain.GoodsCategory;
import com.store.demo.response.GoodsCategoryView;
import com.store.demo.service.GoodsCategoryService;
import com.store.demo.mapper.GoodsCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService{

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public GoodsCategory getById(Integer id){
        return goodsCategoryMapper.selectById(id);
    }
    @Override
    public GoodsCategory select(Map<String, Object> map){
        return goodsCategoryMapper.select(map);
    }

    @Override
    public List<GoodsCategory> selectList(Map<String, Object> map){
        return goodsCategoryMapper.selectList(map);
    }

    @Override
    public List<GoodsCategoryView> selectViewList(Map<String, Object> map) {
        return goodsCategoryMapper.selectViewList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return goodsCategoryMapper.selectCount(map);
    }

    @Override
    public int create(GoodsCategory goodsCategory){
        return goodsCategoryMapper.insert(goodsCategory);
    }

    @Override
    public int update(GoodsCategory goodsCategory){
        return goodsCategoryMapper.update(goodsCategory);
    }

    @Override
    public int updateStatus(GoodsCategory goodsCategory){
        return goodsCategoryMapper.updateStatus(goodsCategory);
    }

    @Override
    public int deleteById(Integer id){
        return goodsCategoryMapper.deleteById(id);
    }

    @Override
    public boolean checkInvalidCategory(List<Integer> list) {
        return goodsCategoryMapper.selectInvalidCountByIdList(list) > 0;
    }
}