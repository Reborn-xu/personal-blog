package com.reborn.springboot.controller.admin;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Permission;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.entity.Role;
import com.reborn.springboot.service.PermissionService;
import com.reborn.springboot.service.RoleService;
import com.reborn.springboot.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("path","roles");
        //model.addAttribute("permissions",permissionService.getPermissionList());
        return "admin/role";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result roleList(@RequestParam Map<String,Object> map){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        PageInfo<Role> roles  = roleService.getRolePage(map);
        return ResultGenerator.getSuccessResult(roles);
    }

    @PostMapping("/save")
    @ResponseBody
    public Result savePermission(Role role){
        String result = roleService.saveRole(role);
//        if (permission.getPermissionId() == -1){
//            permission.setPermissionId();
//        }
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("新增失败");
        }
        return ResultGenerator.getSuccessResult("新增成功");
    }

    @PostMapping("/getPermissions")
    @ResponseBody
    public Result getPermissions(){
        //String result = roleService.getRolePermissionsAndAllPermissions()；
        return null;
    }
}
