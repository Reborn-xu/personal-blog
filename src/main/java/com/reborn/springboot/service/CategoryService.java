package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.BlogCategory;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    List<BlogCategory> getCategoryList();

    PageInfo<BlogCategory> getBlogCategoryPage(Map<String,Object> map);
}
