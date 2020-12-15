package com.reborn.springboot.controller.blog;

import com.github.pagehelper.PageInfo;
import com.reborn.springboot.entity.Blog;
import com.reborn.springboot.entity.BlogComment;
import com.reborn.springboot.entity.Result;
import com.reborn.springboot.entity.vo.BlogDetailVO;
import com.reborn.springboot.service.*;
import com.reborn.springboot.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BlogController {

    private static final String indexName="amaze";//首页模板
    private static final String pageSize = "5";//首页默认博客数量

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

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
    @RequestMapping("/page/{pageNum}")
    public String page(HttpServletRequest request,@PathVariable(name = "pageNum") String pageNum){
        //获取博客分页信息，存入request域中
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("blogStatus",1);
        PageInfo<Blog> blogPageResult = blogService.getBlogsPage(map);
        request.setAttribute("blogPageResult", blogPageResult);

        //所有标签
        request.setAttribute("Tags",tagService.getTagsList());
        //所有分裂
        request.setAttribute("categories", categoryService.getCategoryList());
        //点击最多的博客
        map.clear();
        request.setAttribute("favoriteBlogs",blogService.getFavoriteBlogs(map));
        //获取配置项，存入request域中
        request.setAttribute("configurations",configurationService.getAllConfigurations());
        request.setAttribute("pageName","首页");
        return "/blog/"+indexName+"/index";
    }

    /**
     * 前台博客内容
     * @param blogId
     * @param request
     * @param commentPage
     * @return
     */
    @RequestMapping("/blog/{blogId}")
    public String detail(@PathVariable Long blogId, HttpServletRequest request,
                         @RequestParam(name = "commentPage",required = false,defaultValue = "1")String commentPage,
                         @RequestParam(required = false,defaultValue = "5")String commentSize){
        //获取博客信息
        BlogDetailVO blogDetailVO = blogService.getBlogDetailVOByPrimary(blogId);
        if (blogDetailVO==null){
            //找不到博客，返回404

        }
        //博客内容
        request.setAttribute("blogDetailVO",blogDetailVO);
        //评论列表
        Map<String,Object> map = new HashMap<>();
        map.put("pageNum",commentPage);
        map.put("pageSize",commentSize);
        map.put("blogId",blogId);
        request.setAttribute("commentPageResult", commentService.getCommentsPage(map));
        //配置项
        request.setAttribute("configurations",configurationService.getAllConfigurations());
        request.setAttribute("pageName","博客详情");
        return "/blog/" + indexName + "/detail";
    }

    /**
     * 前台博客 评论提交
     * @param blogComment
     * @return
     */
    @PostMapping("/blog/comment")
    @ResponseBody
    public Result saveComment(BlogComment blogComment){
        String result = commentService.saveComment(blogComment);
        if (!result.equals("success")){
            return ResultGenerator.getFailResult("评论失败");
        }
        return ResultGenerator.getSuccessResult("评论成功");
    }

    /**
     * 前台博客的搜索
     * @param keyword
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/search/{keyword}")
    public String searchBlog(@PathVariable String keyword, Model model,HttpServletRequest request){
        Map<String,Object> params = new HashMap<>();
        params.put("keyword", keyword);
        //分页写死，第一页5条记录
        params.put("pageNum","1");
        params.put("pageSize", pageSize);
        PageInfo<Blog> blogsPage = blogService.getBlogsPage(params);
        model.addAttribute("blogPageResult",blogsPage);
        setConfig(request);
        return "/blog/" + indexName + "/list";
    }

    /**
     * 用标签查找对应的博客
     * @param tagName
     * @return
     */
    @GetMapping("/tag/{tagName}")
    public String searchBlogsByTagName(@PathVariable String tagName,HttpServletRequest request){
        List<Blog> blogs = blogService.getBlogsByTagName(tagName);
        request.setAttribute("blogPageResult", new PageInfo<>(blogs));
        setConfig(request);
        return "/blog/" + indexName + "/list";
    }

    @GetMapping("/category/{categoryId}")
    public String searchBlogsByCategoryId(@PathVariable Integer categoryId,HttpServletRequest request){
        List<Blog> blogs = blogService.getBlogsByCategoryId(categoryId);
        request.setAttribute("blogPageResult",new PageInfo<>(blogs));
        setConfig(request);
        return "/blog/" + indexName + "/list";
    }

    /**
     * 设置博客的配置项
     */
    public void setConfig(HttpServletRequest request){
        //配置项
        request.setAttribute("configurations",configurationService.getAllConfigurations());
    }
}
