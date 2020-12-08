package com.reborn.springboot.aop;

import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.RoleService;
import org.aopalliance.intercept.Joinpoint;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
@Aspect
public class PermissionAOP {

    @Autowired
    private RoleService roleService;

    @Pointcut("execution(* com.reborn.springboot.service.impl.*.*(..))")
    public void cutMethod(){

    }
    //@Before("cutMethod()")
    public void before(Joinpoint joinpoint){
        User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
        String userRole = roleService.getRoleByPrimary(currentUser.getRoleId()).getRoleName();
        //joinpoint.getStaticPart().get
    }

    @Around("cutMethod()")
    public void around(ProceedingJoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return;
        }
        User currentUser = (User) subject.getPrincipal();
        String userRole = roleService.getRoleByPrimary(currentUser.getRoleId()).getRoleName();
        for (Object obj:args){
            if (obj instanceof Map && !userRole.equals("admin")){
                ((Map) obj).put("createBy",userRole);
            }
        }
        try {
            joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return;
    }
}
