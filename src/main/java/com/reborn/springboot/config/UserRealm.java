package com.reborn.springboot.config;

import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.RoleService;
import com.reborn.springboot.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 执行授权逻辑，
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //1、获取登陆用户信息
        User user = (User)principalCollection.getPrimaryPrincipal();
        //2、创建AuthorizationInfo对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //3、添加role信息
        authorizationInfo.addRole(roleService.getRoleByPrimary(user.getRoleId()).getRoleName());
        //4、添加permission信息
        List<Permission> permissions = roleService.getPermissionsByRoleId(user.getRoleId());
        Set<String> urls = new HashSet<>();
        for (Permission permission:permissions){
            urls.add(permission.getPermissionUrl());
        }
        authorizationInfo.setStringPermissions(urls);
        return authorizationInfo;
    }

    /**
     * 执行认证逻辑，登陆认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑..");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.loginCheck(token.getUsername());
        if (user == null){
            //用户名为空
            throw new UnknownAccountException();
        }
        if (user.getLocked().equals(1)){
            //用户锁定
            throw new LockedAccountException();
        }
        //判断密码是否正确
        return new SimpleAuthenticationInfo(user,user.getPassword(),user.getClass().getSimpleName());
    }
}
