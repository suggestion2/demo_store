package com.store.demo.service;

import com.store.demo.domain.Comment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface CommentService {
    Comment getById(Integer id);

    Comment select(Map<String, Object> map);

    List<Comment> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(Comment comment);

    int update(Comment comment);

    int deleteById(Integer id);
}