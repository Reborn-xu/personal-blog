package com.reborn.springboot.controller.admin;

import com.reborn.springboot.entity.Result;
import com.reborn.springboot.service.TagService;
import com.reborn.springboot.utils.ResultGenerator;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/admin/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("")
     public String tagsPage(Model model){
        model.addAttribute("path","tags");
         return "admin/tag";
     }

    @RequestMapping("/list")
    @ResponseBody
    public Result tagsList(@RequestParam Map<String,Object> map){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        return ResultGenerator.getSuccessResult(tagService.getBlogTagsPage(map));
    }
}
