package com.reborn.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class Role implements Serializable {
    private int roleId;

    private String roleName;

    private Set<Permission> permissions;
}
