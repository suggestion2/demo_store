package com.store.demo.service.Impl;

import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.mapper.params.GoodsStockUpdateParams;
import com.store.demo.mapper.sku.GoodsSpecUnitUpdateParams;
import com.store.demo.request.SpecUnitEditForm;
import com.store.demo.service.GoodsSpecUnitService;
import com.store.demo.mapper.GoodsSpecUnitMapper;
import com.store.demo.service.oss.OssService;
import com.store.demo.service.stock.GoodsStocks;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.store.demo.service.oss.ImageConstants.GOODS;

@Service
public class GoodsSpecUnitServiceImpl implements GoodsSpecUnitService{

    @Autowired
    private GoodsSpecUnitMapper goodsSpecUnitMapper;
    @Autowired
    private OssService ossService;
    @Override
    public GoodsSpecUnit getById(Integer id){
        return goodsSpecUnitMapper.selectById(id);
    }
    @Override
    public GoodsSpecUnit select(Map<String, Object> map){
        return goodsSpecUnitMapper.select(map);
    }

    @Override
    public List<GoodsSpecUnit> selectListByGoodsId(Integer goodsId) {
        Map<String,Object> map = new HashMap<>();
        map.put("goodsId",goodsId);
        List<GoodsSpecUnit> list = goodsSpecUnitMapper.selectList(map);
        list.forEach(u->u.setImageUrl(getImage(u.getImageUrl())));
        return list;
    }
    @Override
    public GoodsStocks getStocks(Integer unitId) {
        return  goodsSpecUnitMapper.selectStockById(unitId);
    }

    @Override
    public int updateStocks(List<GoodsStockUpdateParams> list) {
        int result = goodsSpecUnitMapper.updateStocks(list);
        return result;
    }

    @Override
    public int batchUpdate(GoodsSpecUnitUpdateParams params) {
        return goodsSpecUnitMapper.batchUpdate(params);
    }

    private String getImage(String imageUrl){
        return ossService.getPublicObject(GOODS + imageUrl);
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
    public int deleteByGoodsId(Integer id){
        return goodsSpecUnitMapper.deleteByGoodsId(id);
    }

    @Override
    public int batchCreate(List<SpecUnitEditForm> list) {
        return goodsSpecUnitMapper.batchInsert(list);
    }
}