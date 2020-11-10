package com.reborn.springboot.service.impl;

import com.reborn.springboot.dao.UserMapper;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loginCheck(String username) {
        return userMapper.getUserByUsername(username);
    }
}
