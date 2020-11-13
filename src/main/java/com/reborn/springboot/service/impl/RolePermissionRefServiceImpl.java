package com.reborn.springboot.service.impl;

import com.reborn.springboot.dao.RolePermissionRefMappper;
import com.reborn.springboot.entity.RolePermissionRef;
import com.reborn.springboot.service.RolePermissionRefService;
import org.springframework.beans.factory.annotation.Autowired;

public class RolePermissionRefServiceImpl implements RolePermissionRefService {

    @Autowired
    private RolePermissionRefMappper rolePermissionRefMappper;

    @Override
    public RolePermissionRef getByRoleId(Integer roleId) {
        return rolePermissionRefMappper.getByRoleId(roleId);
    }
}
