package com.reborn.springboot.controller.admin;

import com.reborn.springboot.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/configurations")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping("")
    public String configurations(Model model){
        Map<String, String> configurations = configurationService.getAllConfigurations();
        model.addAttribute("configurations",configurations);
        return "admin/configuration";
    }
}
