package com.reborn.springboot.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailController {

    @RequestMapping("/verifyCode")
    public String verifyCode(){
        return "";
    }
}
