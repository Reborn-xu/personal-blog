package com.reborn.springboot.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @RequestMapping(path = {"","/index"})
    public String index(Model model){
        model.addAttribute("path", "index");
        return "admin/index";
    }
}
