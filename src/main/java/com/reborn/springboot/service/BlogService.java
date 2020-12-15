package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Blog;
import com.reborn.springboot.entity.vo.BlogDetailVO;

import java.util.List;
import java.util.Map;

public interface BlogService {
     PageInfo<Blog> getBlogsPage(Map<String,Object> map);

     String saveBlog(Blog blog);

    Blog getBlogByPrimary(Long blogId);

    String updateBlog(Blog blog);

    String deleteBlogs(Integer[] ids);

    BlogDetailVO getBlogDetailVOByPrimary(Long blogId);

    List<Blog> getFavoriteBlogs(Map<String,Object> params);

    List<Blog> getBlogsByTagName(String tagName);

    List<Blog> getBlogsByCategoryId(Integer categoryId);
}
