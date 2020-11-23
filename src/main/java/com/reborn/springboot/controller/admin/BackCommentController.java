package com.reborn.springboot.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/comments")
public class BackCommentController {
    @RequestMapping("")
    public String index(Model model){
        model.addAttribute("path","comments");
        return "admin/comment";
    }
}
