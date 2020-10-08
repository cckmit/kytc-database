/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.database.server.api.impl;

import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.service.ColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 何志同
 * @Date: 2020/9/21 16:50
 * @Description:
 **/
@RestController
@RequestMapping("column")
@Api(tags = "表字段操作")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ColumnApiImpl {
    private final ColumnService columnService;
    @ApiOperation("查询表字段列表")
    @GetMapping(value="list")
    @Cacheable(cacheNames="table:column:list",key = "#database+'_'+#tableName")
    public List<ColumnResponse> list(@RequestParam("database")String database,
                                     @RequestParam("tableName")String tableName){
        return columnService.list(database, tableName);
    }
}