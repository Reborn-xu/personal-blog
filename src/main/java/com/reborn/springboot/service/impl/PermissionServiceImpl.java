package com.reborn.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.PermissionMapper;
import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public String savePermission(Permission permission) {
        int result = permissionMapper.insertPermission(permission);
        return result<=0 ? "fail":"success";
    }

    @Override
    public List<Permission> getPermissionList() {
        return permissionMapper.findPermissionList(new HashMap<>());
    }

    @Override
    public PageInfo<Permission> getPermissionPage(Map<String, Object> map) {
        int pageNum=Integer.parseInt((String)map.get("pageNum"));
        int pageSize=Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> list = permissionMapper.findPermissionList(map);
        return new PageInfo<>(list);
    }
}
