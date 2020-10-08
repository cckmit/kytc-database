/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.remote;

import com.kytc.database.server.config.HttpsConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: 何志同
 * @Date: 2020/9/30 12:50
 * @Description:
 **/
@FeignClient(name="GitFeignClient",url="https://api.github.com",fallbackFactory = GitClientFallback.class,configuration = HttpsConfig.class)
public interface GitClient {
    @GetMapping(value = "/repos/{org}/{project}/branches", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,headers = {"Authorization=token 518e11229c6ceded2918a80f65120e7bff0fa1e9","Accept=application/vnd.github.inertia-preview+json"})
    Object getBranches(@PathVariable("org") String org, @PathVariable("project") String project);
}