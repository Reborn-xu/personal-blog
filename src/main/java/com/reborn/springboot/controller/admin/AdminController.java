package com.reborn.springboot.controller.admin;

import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.entity.vo.UserVo;
import com.reborn.springboot.service.RoleService;
import com.reborn.springboot.service.UserService;
import com.reborn.springboot.utils.RedisUtil;
import com.reborn.springboot.utils.ResultGenerator;
import org.apache.catalina.Session;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

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

    @Autowired
    private UserService userService;

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
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            userVo.setRoleName((roleService.getRoleByPrimary(userVo.getRoleId()).getRoleName()));
            session.setAttribute("user", userVo);
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

    /**
     * 登出
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //1、获取subject
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //session.invalidate();
        return "redirect:/";
    }

    /**
     * 跳到注册页面
     * @return
     */
    @GetMapping("/register")
    public String register(){
        return "/admin/register";
    }

    @PostMapping("/registerCheck")
    public String registerCheck(User user,Model model,HttpServletRequest request){
        if (user == null){
            model.addAttribute("errorMsg2","请完善信息");
            return "/admin/register";
        }
        String verifyCode = request.getParameter("verifyCode");
        if (verifyCode.isEmpty()){
            model.addAttribute("errorMsg2","请输入验证码");
            return "/admin/register";
        }
        Jedis jedis = RedisUtil.getJedisConnection();
        String emailKey = "verify:"+user.getEmail()+":code";
        String emailValue = jedis.get(emailKey);
        if (StringUtils.isEmpty(emailValue)){
            model.addAttribute("errorMsg2","验证码不存在或已过期");
            jedis.close();
            return "/admin/register";
        }
        if (!emailValue.equals(verifyCode)){
            model.addAttribute("errorMsg2","验证码错误");
            jedis.close();
            return "/admin/register";
        }
        String result = userService.registerUser(user);
        if (result.equals("success")){
            jedis.del(emailKey);
            jedis.close();
            return "redirect:/admin/login";
        }
        jedis.close();
        model.addAttribute("errorMsg2","注册失败");
        return "/admin/register";
    }

    @RequestMapping("/profile")
    public String profile(){
        return "admin/profile";
    }

    @GetMapping("/checkUserName")
    @ResponseBody
    public Result checkUserName(@RequestParam("userName") String username){
        if (username == null){
            return ResultGenerator.getFailResult("username is null");
        }
        String result = userService.checkUserName(username);
        if (result.equals("success")){
            return ResultGenerator.getSuccessResult("");
        }
        else {
            return ResultGenerator.getFailResult("用户名已存在");
        }
    }
}
