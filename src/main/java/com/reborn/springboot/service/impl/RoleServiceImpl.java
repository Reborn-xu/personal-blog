package com.reborn.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.RoleMapper;
import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.Role;
import com.reborn.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRoleByPrimary(Integer roleId) {
        return null;
    }

    @Override
    public PageInfo<Role> getRolePage(Map<String, Object> map) {
        int pageNum=Integer.parseInt((String)map.get("pageNum"));
        int pageSize=Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNum, pageSize);
        List<Role> list = roleMapper.findRoleList();
        return new PageInfo<>(list);
    }

    @Override
    public String saveRole(Role role) {
        return null;
    }
}
