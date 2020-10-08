package com.kytc.database.server.api.impl;

import javax.annotation.Resource;

import com.kytc.database.server.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("database")
@Api(tags = "数据库操作")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class DatabaseApiImpl {
	private final DatabaseService databaseService;
	@ApiOperation("查询数据库列表")
	@GetMapping(value="list")
	@Cacheable(cacheNames="database:list")
	public List<String> list(){
		return databaseService.list();
	}
}
