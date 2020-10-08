/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 *//*

package com.kytc.database.server.config;

import com.kytc.database.server.service.DynamicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

*/
/**
 * @Author: 何志同
 * @Date: 2020/9/30 15:56
 * @Description:
 **//*

@Configuration
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class DynamicDatabaseConfig {
    private final DynamicService dynamicService;
    @PostConstruct
    public void initDynamicConfig(){
        log.info("项目启动过程中动态加载数据库配置");
        dynamicService.init();
    }
}*/
