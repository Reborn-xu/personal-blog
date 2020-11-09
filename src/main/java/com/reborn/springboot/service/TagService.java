package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.BlogTag;

import java.util.Map;

public interface TagService {
    PageInfo<BlogTag> getBlogTagsPage(Map<String,Object> map);
}
