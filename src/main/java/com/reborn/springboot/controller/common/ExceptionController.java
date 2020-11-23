package com.reborn.springboot.controller.common;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

    @RequestMapping("/403")
    public String noauth() throws UnauthorizedException{
        throw new UnauthorizedException("无权限");
    }
}
