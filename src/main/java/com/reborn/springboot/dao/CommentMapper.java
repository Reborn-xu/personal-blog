package com.reborn.springboot.dao;

import com.reborn.springboot.entity.BlogComment;

import java.util.List;
import java.util.Map;

public interface CommentMapper {

    List<BlogComment> findCommentList(Map<String, Object> map);

    int insertComment(BlogComment blogComment);
}
