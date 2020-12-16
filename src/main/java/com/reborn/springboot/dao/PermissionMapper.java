package com.reborn.springboot.dao;

import com.reborn.springboot.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionMapper {

    List<Permission> findPermissionList(Map<String, Object> map);

    int insertPermission(Permission permission);

    int deletePermissions(Integer[] ids);
}
