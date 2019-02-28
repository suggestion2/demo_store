package com.store.demo.mapper;

import com.store.demo.domain.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommentMapper {

    Comment selectById(Integer id);

    Comment select(Map<String, Object> map);

    List<Comment> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(Comment comment);

    int update(Comment comment);

    int deleteById(Integer id);
}