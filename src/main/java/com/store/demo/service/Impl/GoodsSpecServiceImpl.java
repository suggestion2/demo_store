package com.store.demo.service.Impl;

import com.store.demo.domain.GoodsSpec;
import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.request.SpecEditForm;
import com.store.demo.service.GoodsSpecService;
import com.store.demo.mapper.GoodsSpecMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsSpecServiceImpl implements GoodsSpecService{

    @Autowired
    private GoodsSpecMapper goodsSpecMapper;

    @Override
    public List<GoodsSpec> selectListByGoodsId(Integer goodsId) {
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",goodsId);
        return goodsSpecMapper.selectList(map);
    }
    @Override
    public GoodsSpec getById(Integer id){
        return goodsSpecMapper.selectById(id);
    }
    @Override
    public GoodsSpec select(Map<String, Object> map){
        return goodsSpecMapper.select(map);
    }

    @Override
    public List<GoodsSpec> selectList(Map<String, Object> map){
        return goodsSpecMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return goodsSpecMapper.selectCount(map);
    }

    @Override
    public int create(GoodsSpec goodsSpec){
        return goodsSpecMapper.insert(goodsSpec);
    }

    @Override
    public int update(GoodsSpec goodsSpec){
        return goodsSpecMapper.update(goodsSpec);
    }

    @Override
    public int deleteById(Integer id){
        return goodsSpecMapper.deleteById(id);
    }

    @Override
    public int batchCreate(List<SpecEditForm> specEditFormList) {
        return goodsSpecMapper.batchInsert(specEditFormList);
    }
}