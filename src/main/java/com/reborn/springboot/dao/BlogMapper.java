package com.reborn.springboot.dao;

import com.reborn.springboot.entity.Blog;

import java.util.List;

public interface BlogMapper {
    List<Blog> findBlogList();

    int insertNewBlog(Blog blog);

    Blog getBlogByPrimary(Long blogId);

    int updateBlogByPrimary(Blog blog);

    int deleteBlogsByPrimary(Integer[] ids);
}
