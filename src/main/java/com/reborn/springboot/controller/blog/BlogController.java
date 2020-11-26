package com.reborn.springboot.controller.blog;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Blog;
import com.reborn.springboot.service.BlogService;
import com.reborn.springboot.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BlogController {

    private static final String indexName="amaze";//首页模板
    private static final String pageSize = "5";//首页默认博客数量

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private BlogService blogService;

    /**
     * 博客首页
     * @param request
     * @return
     */
    @RequestMapping(path = {"/","/index","/index.html"})
    public String index(HttpServletRequest request){
        return this.page(request,"1");
    }

    /**
     * 博客首页第pageNum页
     * @param request
     * @param pageNum
     * @return
     */
    @RequestMapping("/page")
    public String page(HttpServletRequest request,String pageNum){
        //获取博客分页信息，存入request域中
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        PageInfo<Blog> blogPageResult = blogService.getBlogsPage(map);
        request.setAttribute("blogPageResult", blogPageResult);

        //获取配置项，存入request域中
        request.setAttribute("configurations",configurationService.getAllConfigurations());
        request.setAttribute("pageName","首页");
        return "/blog/"+indexName+"/index";
    }

    @RequestMapping("/blog/{blogId}")
    public String detail(@PathVariable Long blogId,HttpServletRequest request){
        request.setAttribute("blogDetailVO",blogService.getBlogDetailVOByPrimary(blogId));
        request.setAttribute("configurations",configurationService.getAllConfigurations());
        request.setAttribute("pageName","博客详情");
        return "/blog/" + indexName + "/detail";
    }
}
