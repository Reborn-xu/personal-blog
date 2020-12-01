package com.reborn.springboot.service.impl;

import com.reborn.springboot.dao.RoleMapper;
import com.reborn.springboot.dao.UserMapper;
import com.reborn.springboot.entity.Role;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User loginCheck(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public String registerUser(User user) {
        List<Role> roleList = roleMapper.findRoleList();
        for (Role role:roleList){
            if (role.getRoleName().equals("normalUser")){
                user.setRoleId(role.getRoleId());
                break;
            }
        }
        return userMapper.insertUser(user) >=1 ? "success" : "fail";
    }
}
