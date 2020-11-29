package com.reborn.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.CommentMapper;
import com.reborn.springboot.entity.BlogComment;
import com.reborn.springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public String saveComment(BlogComment blogComment) {
        return commentMapper.insertComment(blogComment) >= 1 ? "success" : "fail";
    }

    @Override
    public PageInfo getCommentsPage(Map<String, Object> map) {
        int pageNum = Integer.parseInt((String)map.get("pageNum"));
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNum, pageSize);
        List<BlogComment> blogList = commentMapper.findCommentList();
        return new PageInfo<BlogComment>(blogList);
    }
}
