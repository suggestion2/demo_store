package com.store.demo.service.Impl;

import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.service.GoodsSpecUnitService;
import com.store.demo.mapper.GoodsSpecUnitMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsSpecUnitServiceImpl implements GoodsSpecUnitService{

    @Autowired
    private GoodsSpecUnitMapper goodsSpecUnitMapper;

    @Override
    public GoodsSpecUnit getById(Integer id){
        return goodsSpecUnitMapper.selectById(id);
    }
    @Override
    public GoodsSpecUnit select(Map<String, Object> map){
        return goodsSpecUnitMapper.select(map);
    }

    @Override
    public List<GoodsSpecUnit> selectList(Map<String, Object> map){
        return goodsSpecUnitMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return goodsSpecUnitMapper.selectCount(map);
    }

    @Override
    public int create(GoodsSpecUnit goodsSpecUnit){
        return goodsSpecUnitMapper.insert(goodsSpecUnit);
    }

    @Override
    public int update(GoodsSpecUnit goodsSpecUnit){
        return goodsSpecUnitMapper.update(goodsSpecUnit);
    }

    @Override
    public int deleteById(Integer id){
        return goodsSpecUnitMapper.deleteById(id);
    }
}