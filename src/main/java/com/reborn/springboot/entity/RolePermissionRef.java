package com.reborn.springboot.entity;

import java.io.Serializable;

public class RolePermissionRef implements Serializable {
    private Integer rprId;

    private Integer roleId;

    private Integer permissionId;

    private Byte isDeleted;

    public Integer getRprId() {
        return rprId;
    }

    public void setRprId(Integer rprId) {
        this.rprId = rprId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}
