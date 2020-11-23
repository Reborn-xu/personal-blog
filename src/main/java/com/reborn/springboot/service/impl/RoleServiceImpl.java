package com.reborn.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.RoleMapper;
import com.reborn.springboot.dao.RolePermissionRefMapper;
import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.Role;
import com.reborn.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionRefMapper rolePermissionRefMapper;

    @Override
    public Role getRoleByPrimary(Integer roleId) {
        return roleMapper.getRoleByPrimary(roleId);
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

    @Override
    public List<Permission> getPermissionsByRoleId(Integer roleId) {
        return roleMapper.getPermissionsByRoleId(roleId);

    }

    @Override
    public String updateRolePermissions(Integer[] ids) {
        Integer roleId = ids[ids.length-1];
        //System.out.println(ids.length);
        ids[ids.length-1]=null;
        //System.out.println(ids.length);
        //Integer[] permissions =
        if (rolePermissionRefMapper.deleteRolePermissionsByRoleId(roleId)<0){
            return "fail";
        }
        if (rolePermissionRefMapper.insertRolePermissions(roleId,ids)<0){
            return "fail";
        }
        return "success";
    }
}
