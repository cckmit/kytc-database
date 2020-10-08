/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.kytc.framework.database.DynamicDataSource;
import com.kytc.framework.database.MybatisConfig;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: 何志同
 * @Date: 2020/9/10 11:54
 * @Description:
 **/


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({MybatisConfig.class})
@MapperScan("com.kytc.database.dao.mapper")
@EnableAutoConfiguration
@Slf4j
@EnableSwagger2
@EnableSwaggerBootstrapUI
@SpringCloudApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCaching
@EnableFeignClients
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
        //log.info("Bootstrap started!");
    }
}