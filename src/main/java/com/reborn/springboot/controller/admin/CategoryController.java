package com.reborn.springboot.controller.admin;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.BlogCategory;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.service.CategoryService;
import com.reborn.springboot.utils.ResultGenerator;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
@RequestMapping("/admin/categories")
@RequiresRoles(value = {"admin"})
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    @RequestMapping("")
    public String categoryPage(Model model){
        model.addAttribute("path", "categories");
        return "admin/category";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Result categoryList(@RequestParam Map<String,Object> map){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        return ResultGenerator.getSuccessResult(categoryService.getBlogCategoryPage(map));
    }

    @GetMapping("/save")
    @ResponseBody
    public Result saveCategory(){
        String result = "";
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("修改失败");
        }
        return ResultGenerator.getSuccessResult("修改成功");
    }
}
