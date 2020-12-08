package com.reborn.springboot.controller.admin;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Blog;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.entity.User;
import com.reborn.springboot.entity.vo.UserVo;
import com.reborn.springboot.service.BlogService;
import com.reborn.springboot.service.CategoryService;
import com.reborn.springboot.utils.ResultGenerator;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BackBlogController {

    @Autowired
    public BlogService blogService;

    @Autowired
    public CategoryService categoryService;

    /**
     * 跳转到博客管理页面
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogPage(Model model){
        model.addAttribute("path","blogs");
        return "admin/blog";
    }

    /**
     * 全部博客信息（分页信息）
     * @param map
     * @return
     */
    @RequestMapping("/blogs/list")
    @ResponseBody
    public Result blogsList(@RequestParam Map<String,Object> map,HttpSession session){
        if (StringUtils.isEmpty(map.get("pageNum"))||StringUtils.isEmpty(map.get("pageSize"))){
            return ResultGenerator.getFailResult("参数异常");
        }
        /*if (session.getAttribute("user")!=null){
            map.put("roleId",((UserVo)session.getAttribute("user")).getRoleId());
            map.put("nickName",((UserVo)session.getAttribute("user")).getNickName());
        }*/
        PageInfo blogs = blogService.getBlogsPage(map);
        return ResultGenerator.getSuccessResult(blogs);
    }

    /**
     * 跳转到发布博客页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/edit")
    public String editBlog(@RequestParam(value = "blogId",required = false) Long blogId,Model model){
        model.addAttribute("path","edit");
        model.addAttribute("categories",categoryService.getCategoryList());
        if (blogId!=null){
            model.addAttribute("blog", blogService.getBlogByPrimary(blogId));
        }
        return "admin/edit";
    }

    /**
     * 新增博客
     * @return
     */
    @PostMapping("/blogs/save")
    @ResponseBody
    public Result saveBlog(Blog blog,HttpSession session){
        String result = blogService.saveBlog(blog);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("新增失败");
        }
        return ResultGenerator.getSuccessResult("新增成功");
    }

    /**
     * 修改博客
     * @param blog
     * @return
     */
    @PostMapping("/blogs/update")
    @ResponseBody
    public Result updateBlog(Blog blog){
        String result = blogService.updateBlog(blog);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("修改失败");
        }
        return ResultGenerator.getSuccessResult("修改成功");
    }

    /**
     * 删除博客
     * @param ids
     * @return
     */
    @PostMapping("/blogs/delete")
    @ResponseBody
    //@RequiresRoles(value = "admin")
    public Result deleteBlogs(@RequestBody Integer[] ids){
        String result = blogService.deleteBlogs(ids);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("修改失败");
        }
        return ResultGenerator.getSuccessResult("修改成功");
    }


}
