package com.store.demo.service;

import com.store.demo.domain.Goods;
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
}