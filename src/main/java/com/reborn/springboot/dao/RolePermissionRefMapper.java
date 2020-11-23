package com.reborn.springboot.dao;

import com.reborn.springboot.entity.RolePermissionRef;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionRefMapper {
    RolePermissionRef getByRoleId(Integer roleId);

    int deleteRolePermissionsByRoleId(Integer roleId);

    int insertRolePermissions(@Param("roleId") Integer roleId, @Param("permissions") Integer[] permissions);
}
