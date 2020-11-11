package com.reborn.springboot.dao;

import com.reborn.springboot.entity.Permission;

import java.util.List;

public interface PermissionMapper {

    List<Permission> findPermissionList();
}
