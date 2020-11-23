package com.reborn.springboot.controller.common;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler({UnauthorizedException.class})
    public ModelAndView handleShiroException(UnauthorizedException u){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg",u.getMessage());
        modelAndView.setViewName("error/403");
        return modelAndView;
    }


}
