package com.reborn.springboot.service;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.User;

import java.util.Map;

public interface UserService {
    User loginCheck(String username);

    String registerUser(User user);

    PageInfo getUsersPage(Map<String, Object> map);

    User getUserByPrimary(Integer userId);

    String editUserRole(Integer userId, Integer roleId);

}
