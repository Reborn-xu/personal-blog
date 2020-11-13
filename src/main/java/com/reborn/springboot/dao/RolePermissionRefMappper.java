package com.reborn.springboot.dao;

import com.reborn.springboot.entity.RolePermissionRef;

public interface RolePermissionRefMappper {
    RolePermissionRef getByRoleId(Integer roleId);
}
