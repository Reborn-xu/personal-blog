package com.reborn.springboot.controller.admin;

import com.reborn.springboot.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户管理的controller层
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {

    @GetMapping("")
    public String index(){
        return "admin/user";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result list(){

    }
}
