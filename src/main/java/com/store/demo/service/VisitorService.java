package com.store.demo.service;

import com.store.demo.domain.Visitor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface VisitorService {
    Visitor getById(Integer id);

    Visitor getByUuid(String uuid);

    Visitor select(Map<String, Object> map);

    List<Visitor> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    Visitor create();

    int update(Visitor visitor);

    int deleteById(Integer id);
}