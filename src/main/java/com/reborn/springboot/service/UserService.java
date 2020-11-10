package com.reborn.springboot.service;

import com.reborn.springboot.entity.User;

public interface UserService {
    User loginCheck(String username);
}
