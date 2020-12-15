package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.BlogTag;

import java.util.List;
import java.util.Map;

public interface TagService {
    PageInfo<BlogTag> getBlogTagsPage(Map<String,Object> map);

    List<BlogTag> getTagsList();

    BlogTag getTagsByPrimary(Integer tagId);
}
