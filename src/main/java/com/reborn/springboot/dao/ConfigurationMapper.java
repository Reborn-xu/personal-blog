package com.reborn.springboot.dao;

import com.reborn.springboot.entity.BlogConfiguration;

import java.util.List;

public interface ConfigurationMapper {
    List<BlogConfiguration> getAllConfigurations();
}
