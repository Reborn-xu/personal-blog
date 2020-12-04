package com.reborn.springboot.controller.admin;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.UserService;
import com.reborn.springboot.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户管理的controller层
 */
@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("path","users");
        return "admin/user";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> map){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        PageInfo blogs = userService.getUsersPage(map);
        return ResultGenerator.getSuccessResult(blogs);
    }
}
