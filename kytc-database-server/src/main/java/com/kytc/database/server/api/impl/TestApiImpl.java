/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.api.impl;

import com.kytc.database.server.remote.GitClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Author: 何志同
 * @Date: 2020/9/30 13:01
 * @Description:
 **/
@RestController
@RequestMapping("test")
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class TestApiImpl {
    private final GitClient gitClient;
    @GetMapping("test")
    public Object getBranches(@RequestParam("org")String org,
                              @RequestParam("project")String project){
        MDC.put("traceId", UUID.randomUUID().toString());
        log.info("/test/test controller,org:{},project:{}",org,project);
        return gitClient.getBranches(org, project);
    }
}