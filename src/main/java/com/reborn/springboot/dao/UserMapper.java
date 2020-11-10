package com.reborn.springboot.dao;

import com.reborn.springboot.entity.User;

public interface UserMapper {
    User getUserByUsername(String username);
}
