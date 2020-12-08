package com.reborn.springboot.aop;

import com.reborn.springboot.entity.Blog;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.entity.vo.UserVo;
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

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Component
@Aspect
public class PermissionAOP {

    @Autowired
    private RoleService roleService;

    @Autowired
    private HttpSession session;

    @Pointcut("execution(* com.reborn.springboot.controller.admin.BackBlogController.*(..))")
    public void blogCutMethod(){

    }
    //@Before("cutMethod()")
    public void before(Joinpoint joinpoint){
        User currentUser = (User)SecurityUtils.getSubject().getPrincipal();
        String userRole = roleService.getRoleByPrimary(currentUser.getRoleId()).getRoleName();
        //joinpoint.getStaticPart().get
    }

    @Around("blogCutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] args = joinPoint.getArgs();
        UserVo user = (UserVo)session.getAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            User currentUser = (User) subject.getPrincipal();
            String userRole = roleService.getRoleByPrimary(currentUser.getRoleId()).getRoleName();
            String methodName = joinPoint.getSignature().getName();
            for (Object obj:args){
                //查询博客列表
                if (obj instanceof Map && !userRole.equals("admin")){
                    ((Map) obj).put("createBy",currentUser.getNickName());
                    break;
                }
                //obj instanceof Blog &&
                else if (methodName.contains("save")){
                    //((Blog) obj).setCreateBy(currentUser.getNickName());
                    Class<?> objClass = obj.getClass();
                    Method method = objClass.getMethod("setCreateBy", String.class);
                    method.invoke(obj, currentUser.getNickName());
                    break;
                }else if (methodName.contains("update")){
                    Class<?> objClass = obj.getClass();
                    Method method = objClass.getMethod("setUpdateBy", String.class);
                    method.invoke(obj, currentUser.getNickName());
                    break;
                }
            }
        }
        Object result = null;
        try {
             result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
