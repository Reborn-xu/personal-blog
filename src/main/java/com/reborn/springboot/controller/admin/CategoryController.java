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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin/categories")
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

    @PostMapping("/save")
    @ResponseBody
    public Result saveCategory(BlogCategory blogCategory){
        String result = categoryService.saveCategory(blogCategory);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("保存失败");
        }
        return ResultGenerator.getSuccessResult("保存成功");
    }

    @PostMapping("/update")
    @ResponseBody
    public Result updateCategory(BlogCategory blogCategory){
        String result = categoryService.updateCategoryByPrimary(blogCategory);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("修改失败");
        }
        return ResultGenerator.getSuccessResult("修改成功");
    }

    @GetMapping("/getCategoryByPrimary")
    @ResponseBody
    public Result getCategoryByPrimary(@RequestParam Integer categoryId){
        BlogCategory category = categoryService.getCategoryByPrimary(categoryId);
        return ResultGenerator.getSuccessResult(category);
    }
}
