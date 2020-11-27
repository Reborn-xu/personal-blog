package com.reborn.springboot.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reborn.springboot.dao.BlogMapper;
import com.reborn.springboot.dao.BlogTagRelationMapper;
import com.reborn.springboot.dao.CategoryMapper;
import com.reborn.springboot.dao.TagMapper;
import com.reborn.springboot.entity.Blog;
import com.reborn.springboot.entity.BlogCategory;
import com.reborn.springboot.entity.BlogTag;
import com.reborn.springboot.entity.BlogTagRelation;
import com.reborn.springboot.entity.vo.BlogDetailVO;
import com.reborn.springboot.service.BlogService;
import com.reborn.springboot.utils.MarkDownUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;

    /**
     * 前台获取博客信息
     * @param blogId
     * @return
     */
    @Override
    public BlogDetailVO getBlogDetailVOByPrimary(Long blogId) {
        Blog blog = blogMapper.getBlogByPrimary(blogId);
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        return blogDetailVO;
    }

    /**
     * 前台获取博客vo信息
     * @param blog
     * @return
     */
    private BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog!=null&&blog.getBlogStatus()==1){
            //更新blogviews,访问量+1
            blog.setBlogViews(blog.getBlogViews()+1);
            blogMapper.updateBlogByPrimary(blog);

            BlogDetailVO blogDetailVO = new BlogDetailVO();
            //深拷贝blog到blogetailvo
            BeanUtils.copyProperties(blog,blogDetailVO);
            //设置blogContent的markdown格式转换
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            //设置blogTags
            if (blog.getBlogTags()!=null){
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            //设置categoryIcon，分类图标
            BlogCategory category = categoryMapper.findCategoryById(blogDetailVO.getBlogCategoryId());
            blogDetailVO.setBlogCategoryIcon(category.getCategoryIcon());
            //设置commentCount


            return blogDetailVO;
        }
        return null;
    }

    @Override
    public PageInfo<Blog> getBlogsPage(Map<String,Object> map) {
        int pageNum=Integer.parseInt((String)map.get("pageNum"));
        int pageSize=Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogList = blogMapper.findBlogList();
        return new PageInfo<Blog>(blogList);
    }

    @Override
    public Blog getBlogByPrimary(Long blogId) {
        return blogMapper.getBlogByPrimary(blogId);
    }

    @Override
    public String deleteBlogs(Integer[] ids) {
        return blogMapper.deleteBlogsByPrimary(ids)>=1 ? "success" : "fail";
    }

    /**
     * 修改博客
     * @param blog
     * @return
     */
    @Override
    public String updateBlog(Blog blog) {
        //1、查出分类的名称，传入blog
        BlogCategory category = categoryMapper.findCategoryById(blog.getBlogCategoryId());
        if (category == null){
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认");
        }
        blog.setBlogCategoryName(category.getCategoryName());
        //2、更新标签内容
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        Map<String,Object> map = new HashMap<>();
        map.put("blogId", blog.getBlogId());
        blogTagRelationMapper.deleteBlogTagRelation(map);
        map.clear();
        for (String tagName:tags){
            map.put("tagName",tagName);
            BlogTag blogTag = tagMapper.selectTag(map);
            if (blogTag==null){
                //查不到标签，则新增标签
                blogTag=new BlogTag();
                blogTag.setTagName(tagName);
                if (tagMapper.insertNewTag(blogTag)<=0){
                    return "insert failed";
                }
            }
            //4、更新标签和博客中间表
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(blogTag.getTagId());
            blogTagRelationMapper.insertNew(blogTagRelation);
        }
        blog.setUpdateTime(new Date());
        int insertResult = blogMapper.updateBlogByPrimary(blog);
        if (insertResult<=0){
            return "fail";
        }
        return "success";
    }

    /**
     * 新增博客
     * @param blog
     * @return
     */
    @Override
    public String saveBlog(Blog blog) {

        //1、查出分类的名称，传入blog
        BlogCategory category = categoryMapper.findCategoryById(blog.getBlogCategoryId());
        if (category == null){
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认");
        }
        blog.setBlogCategoryName(category.getCategoryName());
        //2、插入新增博客
        int insertResult = blogMapper.insertNewBlog(blog);
        if (insertResult<=0){
            return "fail";
        }
        //3、更新标签内容
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        Map<String,Object> map = new HashMap<>();
        for (String tagName:tags){
            map.put("tagName",tagName);
            BlogTag blogTag = tagMapper.selectTag(map);
            if (blogTag==null){
                //查不到标签，则新增标签
                blogTag=new BlogTag();
                blogTag.setTagName(tagName);
                if (tagMapper.insertNewTag(blogTag)<=0){
                    return "insert failed";
                }
            }
            //4、更新标签和博客中间表
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(blogTag.getTagId());
            blogTagRelationMapper.insertNew(blogTagRelation);
        }

        return "success";
    }
}
