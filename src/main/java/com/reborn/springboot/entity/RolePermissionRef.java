package com.reborn.springboot.entity;

import java.io.Serializable;

public class RolePermissionRef implements Serializable {
    private int rprId;

    private int roleId;

    private int permissionId;

    private byte isDeleted;

    public int getRprId() {
        return rprId;
    }

    public void setRprId(int rprId) {
        this.rprId = rprId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}
