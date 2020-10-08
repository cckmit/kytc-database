/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.remote;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: 何志同
 * @Date: 2020/9/30 13:05
 * @Description:
 **/
@Component
@Slf4j
public class GitClientFallback implements FallbackFactory<GitClient> {
    @Override
    public GitClient create(Throwable throwable) {
        return new GitClient() {
            @Override
            public Object getBranches(String org, String project) {
                log.info("org:{},project:{}",org,project,throwable);
                return null;
            }
        };
    }
}