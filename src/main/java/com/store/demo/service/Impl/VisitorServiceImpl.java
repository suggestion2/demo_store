package com.store.demo.service.Impl;

import com.store.demo.domain.Visitor;
import com.store.demo.service.VisitorService;
import com.store.demo.mapper.VisitorMapper;
import com.sug.core.util.JUGUUIDGenerator;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorServiceImpl implements VisitorService{

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public Visitor getByUuid(String uuid) {
        Map<String, Object> map = new HashMap<>();
        map.put("uuid",uuid);
        return visitorMapper.select(map);
    }

    @Override
    public Visitor getById(Integer id){
        return visitorMapper.selectById(id);
    }
    @Override
    public Visitor select(Map<String, Object> map){
        return visitorMapper.select(map);
    }

    @Override
    public List<Visitor> selectList(Map<String, Object> map){
        return visitorMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return visitorMapper.selectCount(map);
    }

    @Override
    public Visitor create(){
        Visitor visitor = new Visitor();
        visitor.setUuid(JUGUUIDGenerator.generate());
        visitorMapper.insert(visitor);
        return visitor;
    }

    @Override
    public int update(Visitor visitor){
        return visitorMapper.update(visitor);
    }

    @Override
    public int deleteById(Integer id){
        return visitorMapper.deleteById(id);
    }
}