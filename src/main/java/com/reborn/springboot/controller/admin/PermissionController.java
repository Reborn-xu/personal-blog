package com.reborn.springboot.controller.admin;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.service.PermissionService;
import com.reborn.springboot.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/admin/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("path","permissions");
        return "admin/permission";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result permissionsList(@RequestParam Map<String,Object> map){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        PageInfo<Permission> permissions = permissionService.getPermissionPage(map);
        return ResultGenerator.getSuccessResult(permissions);
    }
}
