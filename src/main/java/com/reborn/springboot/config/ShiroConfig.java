package com.reborn.springboot.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * 添加shiro内置的过滤器
         * shiro内置过滤器，可以实现权限相关的拦截器
         *      anon：无需认证（登陆）可以直接访问
         *      authc：必须认证才可访问
         *      user: 如果使用rememberMe的功能可以直接访问
         *       perms： 该资源必须得到资源权限才可以访问
         *       role: 该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();

        //认证过滤器anon和authc
        filterMap.put("/admin/dist/**","anon");
        filterMap.put("/admin/plugins/**","anon");
        filterMap.put("/admin/loginCheck","anon");
        filterMap.put("/admin/**","authc");

        //授权过滤器perms，role
        filterMap.put("/admin/tags/**","perms[user:add]");

        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @param userRealm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean("userRealm")
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

}
