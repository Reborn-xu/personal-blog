package com.reborn.springboot.dao;

import com.reborn.springboot.entity.BlogComment;

import java.util.List;

public interface CommentMapper {

    List<BlogComment> findCommentList();

    int insertComment(BlogComment blogComment);
}
