package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Role;

import java.util.Map;

public interface RoleService {
    Role getRoleByPrimary(Integer roleId);

    PageInfo<Role> getRolePage(Map<String, Object> map);

    String saveRole(Role role);
}
