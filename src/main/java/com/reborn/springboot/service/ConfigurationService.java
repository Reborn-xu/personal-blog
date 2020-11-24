package com.reborn.springboot.service;

import com.reborn.springboot.entity.BlogConfiguration;

import java.util.List;
import java.util.Map;

public interface ConfigurationService {
    Map<String,String> getAllConfigurations();
}
