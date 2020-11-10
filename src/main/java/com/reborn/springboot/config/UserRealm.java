package com.reborn.springboot.config;

import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑，
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");

        return null;
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
        return new SimpleAuthenticationInfo("",user.getPassword(),"");
    }
}
