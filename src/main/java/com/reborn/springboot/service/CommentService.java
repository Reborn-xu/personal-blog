package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.BlogComment;

import java.util.Map;

public interface CommentService {

    PageInfo getCommentsPage(Map<String, Object> map);

    String saveComment(BlogComment blogComment);
}
