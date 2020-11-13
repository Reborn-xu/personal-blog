package com.reborn.springboot.controller.common;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler({UnauthorizedException.class})
    public String handleShiroException(){
        return "redirect:error/403";
    }

    @RequestMapping("/403")
    public String error403(){
        return "error/403";
    }
}
