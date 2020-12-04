package com.reborn.springboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.RoleMapper;
import com.reborn.springboot.dao.UserMapper;
import com.reborn.springboot.entity.Blog;
import com.reborn.springboot.entity.Role;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.entity.vo.UserVo;
import com.reborn.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    @Override
    public PageInfo getUsersPage(Map<String, Object> map) {
        int pageNum=Integer.parseInt((String)map.get("pageNum"));
        int pageSize=Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNum, pageSize);
        List<UserVo> blogList = userMapper.findUserList(map);
        for (int i=0;i<blogList.size();i++){
            int roleId = blogList.get(i).getRoleId();
            Role role = roleMapper.getRoleByPrimary(roleId);
            blogList.get(i).setRoleName(role.getRoleName());
        }
        return new PageInfo<UserVo>(blogList);
    }
}
