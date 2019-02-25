package com.store.demo.mapper;

import com.store.demo.domain.Visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface VisitorMapper {

    Visitor selectById(Integer id);

    Visitor select(Map<String, Object> map);

    List<Visitor> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(Visitor visitor);

    int update(Visitor visitor);

    int deleteById(Integer id);
}