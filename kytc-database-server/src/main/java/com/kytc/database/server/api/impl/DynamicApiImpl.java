/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.api.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kytc.database.server.service.DynamicService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.service.ColumnService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

/**
 * @Author: 何志同
 * @Date: 2020/9/21 16:50
 * @Description:
 **/
@RestController
@RequestMapping("dynamic")
@Api(tags = "动态SQL操作")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class DynamicApiImpl {
    private final DynamicService dynamicService;
    @ApiOperation("查询数据库操作")
    @PostMapping(value="/{database}/query")
    public List<Map<String,Object>> list(@PathVariable("database")String database,
                                         @RequestBody String sql){
        MDC.put("traceId", UUID.randomUUID().toString());
        return dynamicService.select(database, sql);
    }
}