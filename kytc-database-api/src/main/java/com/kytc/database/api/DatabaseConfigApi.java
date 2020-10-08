package com.kytc.database.api;

import com.kytc.database.request.DatabaseConfigRequest;
import com.kytc.database.response.DatabaseConfigResponse;

import com.kytc.framework.web.common.BasePageResponse;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

@Api(tags = "动态数据库配置操作")
@RequestMapping("/database/config")
public interface DatabaseConfigApi {

	@ApiOperation("查询动态数据库配置列表")
	@PostMapping("/infos")
	BasePageResponse<DatabaseConfigResponse> listByCondition(
            @RequestBody DatabaseConfigRequest request,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize);

	@ApiOperation("添加动态数据库配置数据")
	@PostMapping("/info")
	boolean add(@RequestBody @Valid DatabaseConfigRequest request);

	@ApiOperation("修改动态数据库配置数据")
	@PutMapping("/info")
	boolean update(@RequestBody @Valid DatabaseConfigRequest request);

	@ApiOperation("删除动态数据库配置数据")
	@DeleteMapping("/{id}")
	boolean delete(@PathVariable("id") Long id);

	@ApiOperation("查询动态数据库配置详情")
	@GetMapping("/{id}")
	DatabaseConfigResponse detail(@PathVariable("id") Long id);
}
