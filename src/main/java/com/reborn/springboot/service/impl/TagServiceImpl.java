package com.reborn.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.TagMapper;
import com.reborn.springboot.entity.BlogTag;
import com.reborn.springboot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public PageInfo<BlogTag> getBlogTagsPage(Map<String, Object> map) {
        int pageNum=Integer.parseInt((String)map.get("pageNum"));
        int pageSize=Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(tagMapper.findTagList());
    }

    @Override
    public List<BlogTag> getTagsList() {
        return tagMapper.findTagList();
    }

    @Override
    public BlogTag getTagsByPrimary(Integer tagId) {
        Map<String,Object> params = new HashMap<>();
        params.put("tagId", tagId);
        return tagMapper.selectTag(params);
    }
}
