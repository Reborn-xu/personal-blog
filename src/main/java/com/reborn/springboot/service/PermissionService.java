package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Permission;

import java.util.Map;

public interface PermissionService {
    PageInfo<Permission> getPermissionPage(Map<String, Object> map);
}
