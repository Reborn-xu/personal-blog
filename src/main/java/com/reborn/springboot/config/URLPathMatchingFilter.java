package com.reborn.springboot.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * url拦截器
 */
public class URLPathMatchingFilter extends PathMatchingFilter {

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String requestURL = getPathWithinApplication(request);
        System.out.println("请求的url："+requestURL);
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()){
            //如果没登陆，进入登陆流程
            WebUtils.issueRedirect(request,response,"/login");
            return false;
        }

        //从session中读取当前用户的权限url列表
        return super.onPreHandle(request, response, mappedValue);
    }
}
