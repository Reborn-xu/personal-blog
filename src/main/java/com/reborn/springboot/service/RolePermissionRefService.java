package com.reborn.springboot.service;

import com.reborn.springboot.entity.RolePermissionRef;

public interface RolePermissionRefService {
    RolePermissionRef getByRoleId(Integer roleId);
}
