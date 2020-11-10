package com.reborn.springboot.controller.admin;

import com.reborn.springboot.entity.User;
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

@RequestMapping("/admin")
@Controller
public class AdminController {
    @RequestMapping(path = {"","/index"})
    public String index(Model model){
        model.addAttribute("path", "index");
        return "admin/index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/loginCheck")
    public String loginCheck(String username,String password,Model model){
        System.out.println(username+password);
        /**
         * shiro编写认证操作
         */
        //1、获取subject
        Subject subject = SecurityUtils.getSubject();
        //2、封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
        }catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误
            model.addAttribute("msg", "密码错误");
            return "login";
        }catch (LockedAccountException e){
            model.addAttribute("msg","账号锁定");
            return "login";
        }
        return "admin/index";
    }
}
