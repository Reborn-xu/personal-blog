package com.reborn.springboot.controller.admin;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.service.RoleService;
import com.reborn.springboot.service.UserService;
import com.reborn.springboot.utils.ResultGenerator;
import org.apache.ibatis.annotations.Param;
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

    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("path","users");
        model.addAttribute("roles",roleService.getRoleList());
        return "admin/user";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> map){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        PageInfo users = userService.getUsersPage(map);
        return ResultGenerator.getSuccessResult(users);
    }

    @GetMapping("/getUserRole")
    @ResponseBody
    public Result getUserRole(Integer userId){
        if (userId==null){
            return ResultGenerator.getFailResult("用户id为空");
        }
        User user = userService.getUserByPrimary(userId);
        return ResultGenerator.getSuccessResult(user);
    }

    /**
     * 修改用户的角色
     * @param userId
     * @param roleId
     * @return
     */
    @GetMapping("/editUserRole")
    @ResponseBody
    public Result editUserRole(@RequestParam("userId") Integer userId,
                               @RequestParam("roleId") Integer roleId){
        String result = userService.editUserRole(userId, roleId);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("修改失败");
        }
        return ResultGenerator.getSuccessResult("修改成功");
    }
}
