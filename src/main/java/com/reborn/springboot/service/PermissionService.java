package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    PageInfo<Permission> getPermissionPage(Map<String, Object> map);

    String savePermission(Permission permission);

    List<Permission> getPermissionList();

    String deletePermissions(Integer[] ids);
}
