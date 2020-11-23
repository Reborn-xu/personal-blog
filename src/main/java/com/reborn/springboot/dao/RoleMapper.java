package com.reborn.springboot.dao;

import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.Role;

import java.util.List;

public interface RoleMapper {
    List<Role> findRoleList();

    List<Permission> getPermissionsByRoleId(Integer roleId);

    Role getRoleByPrimary(Integer roleId);
}