package com.reborn.springboot.dao;

import com.reborn.springboot.entity.User;
import com.reborn.springboot.entity.vo.UserVo;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    User getUserByUsername(String username);

    int insertUser(User user);

    List<UserVo> findUserList(Map<String, Object> map);
}
