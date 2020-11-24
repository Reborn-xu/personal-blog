package com.reborn.springboot.service.impl;

import com.reborn.springboot.dao.ConfigurationMapper;
import com.reborn.springboot.entity.BlogConfiguration;
import com.reborn.springboot.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService{

    @Autowired
    private ConfigurationMapper configurationMapper;

    @Override
    public Map<String,String> getAllConfigurations() {
        List<BlogConfiguration> configurations = configurationMapper.getAllConfigurations();
        Map<String,String> map = new HashMap<>();
        for (BlogConfiguration configuration:configurations){
            map.put(configuration.getConfigName(),configuration.getConfigValue());
        }
        return map;
    }
}
