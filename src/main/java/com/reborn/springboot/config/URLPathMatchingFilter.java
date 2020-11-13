package com.reborn.springboot.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            WebUtils.issueRedirect(request,response,"/admin/login");
            return false;
        }

        //从session中读取当前用户的权限url列表
        Set<String> urls = (Set<String>) subject.getSession();
        if (urls.contains(requestURL)){
            return true;
        }

        //没有权限
        if (isAjax((HttpServletRequest) request)){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("code",0);
            map.put("msg","没有访问权限");
            writer.write(JSONObject.toJSONString(map));
        }else {
            WebUtils.issueRedirect(request,response,"/403");
        }
        return false;
        //return super.onPreHandle(request, response, mappedValue);
    }

    public static boolean isAjax(HttpServletRequest request){
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With").toString()));
    }
}
