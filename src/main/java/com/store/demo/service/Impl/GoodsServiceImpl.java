package com.store.demo.service.Impl;

import com.store.demo.domain.Goods;
import com.store.demo.mapper.GoodsSpecUnitMapper;
import com.store.demo.service.GoodsService;
import com.store.demo.mapper.GoodsMapper;
import com.store.demo.service.stock.GoodsStocks;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static com.store.demo.constants.GoodsConstants.FOR_SALE;
import static com.store.demo.constants.GoodsConstants.STOCK;

@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsSpecUnitMapper goodsSpecUnitMapper;

    @Override
    public Goods getById(Integer id){
        return goodsMapper.selectById(id);
    }
    @Override
    public Goods select(Map<String, Object> map){
        return goodsMapper.select(map);
    }

    @Override
    public List<Goods> selectList(Map<String, Object> map){
        return goodsMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return goodsMapper.selectCount(map);
    }

    @Override
    public int create(Goods goods){
        return goodsMapper.insert(goods);
    }

    @Override
    public int update(Goods goods){
        return goodsMapper.update(goods);
    }

    @Override
    public int deleteById(Integer id){
        return goodsMapper.deleteById(id);
    }
    @Override
    public Goods resetStatus(Goods goods) {
        goodsMapper.updateStatus(goods);
        return goods;
    }

    @Override
    public Map<String,Integer> getStocks(List<GoodsStocks> checkList) {
        return getStocksMap(checkList);
    }

    private Map<String,Integer> getStocksMap(List<GoodsStocks> checkList){
        Set<Integer> unitIdSet = new HashSet<>();
        //保存checkList集合的GoodsStocks的unitId
        checkList.forEach(s->{
            unitIdSet.add(s.getUnitId());
        });
        //用unit的id 去数据库查库存
        List<GoodsStocks> unitStocksList = goodsSpecUnitMapper.selectStockList(new ArrayList<>(unitIdSet));
        //把查出来的库存存进Map集合用goodsStocks.getId() + ":" + goodsStocks.getUnitId()当key，库存当值
        return unitStocksList.stream().collect(Collectors.toMap((GoodsStocks goodsStocks)->goodsStocks.getId() + ":" + goodsStocks.getUnitId(),GoodsStocks::getStocks));

    }
}