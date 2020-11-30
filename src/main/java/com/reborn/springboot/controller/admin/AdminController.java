package com.reborn.springboot.controller.admin;

import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.RoleService;
import org.apache.catalina.Session;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    private RoleService roleService;

    /**
     * 后台首页
     * @param model
     * @return
     */
    @RequestMapping(path = {"","/index"})
    public String index(Model model){
        model.addAttribute("path", "index");
        return "admin/index";
    }

    /**
     * 跳转到登录页
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    /**
     * 登陆校验
     * @param username
     * @param password
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/loginCheck")
    public String loginCheck(String username, String password, Model model, HttpSession session){
        //System.out.println(username+password);
        /**
         * shiro编写认证操作
         */
        //1、获取subject
        Subject subject = SecurityUtils.getSubject();
        //2、封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            //3、登陆
            subject.login(token);
            //4、获取用户数据存入session中
            User user=(User) subject.getPrincipal();
            session.setAttribute("user", user);
            //session.setAttribute("role", );

            //该用户如果有角色
            if (user.getRoleId()!=null){
                List<Permission> permissions = roleService.getPermissionsByRoleId(user.getRoleId());
                Set<String> urls = new HashSet<>();
                for (Permission permission:permissions){
                    urls.add(permission.getPermissionUrl());
                }
                subject.getSession().setAttribute("urls",urls);
            }

        }catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "/admin/login";
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误
            model.addAttribute("msg", "密码错误");
            return "/admin/login";
        }catch (LockedAccountException e){
            model.addAttribute("msg","账号锁定");
            return "/admin/login";
        }
        return "redirect:/admin/index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //1、获取subject
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //session.invalidate();
        return "redirect:/";
    }

    @RequestMapping("/profile")
    public String profile(){
        return "admin/profile";
    }
}
