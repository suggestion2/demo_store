package com.store.demo.service.Impl;

import com.store.demo.domain.Comment;
import com.store.demo.service.CommentService;
import com.store.demo.mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Comment getById(Integer id){
        return commentMapper.selectById(id);
    }
    @Override
    public Comment select(Map<String, Object> map){
        return commentMapper.select(map);
    }

    @Override
    public List<Comment> selectList(Map<String, Object> map){
        return commentMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return commentMapper.selectCount(map);
    }

    @Override
    public int create(Comment comment){
        return commentMapper.insert(comment);
    }

    @Override
    public int update(Comment comment){
        return commentMapper.update(comment);
    }

    @Override
    public int deleteById(Integer id){
        return commentMapper.deleteById(id);
    }
}