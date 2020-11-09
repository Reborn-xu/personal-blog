package com.reborn.springboot.dao;

import com.reborn.springboot.entity.BlogTagRelation;

import java.util.Map;

public interface BlogTagRelationMapper {

    int insertNew(BlogTagRelation blogTagRelation);

    int deleteBlogTagRelation(Map<String, Object> map);
}
