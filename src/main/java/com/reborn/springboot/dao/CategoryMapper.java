package com.reborn.springboot.dao;

import com.reborn.springboot.entity.BlogCategory;

import java.util.List;

public interface CategoryMapper {
    List<BlogCategory> findCategoryList();

    BlogCategory findCategoryById(int categoryId);
}
