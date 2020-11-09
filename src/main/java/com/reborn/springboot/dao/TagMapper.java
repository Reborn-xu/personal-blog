package com.reborn.springboot.dao;

import com.reborn.springboot.entity.BlogTag;

import java.util.List;
import java.util.Map;

public interface TagMapper {
    List<BlogTag> findTagList();

    BlogTag selectTag(Map<String,Object> map);

    int insertNewTag(BlogTag blogTag);
}
