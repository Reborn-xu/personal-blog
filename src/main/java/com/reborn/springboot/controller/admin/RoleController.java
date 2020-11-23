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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 跳转到角色管理页面
     * @param model
     * @return
     */
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("path","roles");
        //model.addAttribute("permissions",permissionService.getPermissionList());
        return "admin/role";
    }

    /**
     * 返回角色列表
     * @param map
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result roleList(@RequestParam Map<String,Object> map){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        PageInfo<Role> roles  = roleService.getRolePage(map);
        return ResultGenerator.getSuccessResult(roles);
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
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

    /**
     * 获取roleId角色 拥有的权限
     * @param roleId
     * @return
     */
    @GetMapping("/getPermissions")
    @ResponseBody
    public Result getPermissions(Integer roleId){
        List<Permission> permissions = roleService.getPermissionsByRoleId(roleId);
        return ResultGenerator.getSuccessResult(permissions);
    }

    /**
     * 更新 roleId=ids[length-1]的角色的权限
     * @param ids
     * @return
     */
    @PostMapping("/updatePermissions")
    @ResponseBody
    public Result updatePermissions(@RequestBody Integer[] ids){
        String result = roleService.updateRolePermissions(ids);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("编辑失败");
        }
        return ResultGenerator.getSuccessResult("编辑成功");
    }
}
