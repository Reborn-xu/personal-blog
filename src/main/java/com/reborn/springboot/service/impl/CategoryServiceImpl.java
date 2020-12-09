package com.reborn.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.CategoryMapper;
import com.reborn.springboot.entity.BlogCategory;
import com.reborn.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    public CategoryMapper categoryMapper;

    @Override
    public List<BlogCategory> getCategoryList() {
        return categoryMapper.findCategoryList();
    }

    @Override
    public PageInfo<BlogCategory> getBlogCategoryPage(Map<String, Object> map) {
        int pageNum=Integer.parseInt((String)map.get("pageNum"));
        int pageSize=Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNum, pageSize);
        List<BlogCategory> categoryList = categoryMapper.findCategoryList();
        return new PageInfo<>(categoryList);
    }

    @Override
    public String updateCategoryByPrimary(BlogCategory blogCategory) {
        categoryMapper.updateCategoryByPrimary(blogCategory);
        return null;
    }

    @Override
    public String saveCategory(BlogCategory blogCategory) {
        categoryMapper.insertCategory(blogCategory);
        return null;
    }
}
