package com.reborn.springboot.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorizedController {

    @RequestMapping("/noauth")
    public String noauth(){
        return "noauth";
    }
}
