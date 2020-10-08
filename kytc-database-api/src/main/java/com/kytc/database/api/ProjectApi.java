package com.kytc.database.api;

import com.kytc.database.request.ProjectRequest;
import com.kytc.database.response.ProjectResponse;

import com.kytc.framework.web.common.BasePageResponse;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;

@Api(tags = "项目操作")
@RequestMapping("/project")
public interface ProjectApi {

	@ApiOperation("查询项目列表")
	@PostMapping("/infos")
	BasePageResponse<ProjectResponse> listByCondition(
            @RequestBody ProjectRequest request,
            @RequestParam("page") int page,
            @RequestParam("pageSize") int pageSize);

	@ApiOperation("添加项目数据")
	@PostMapping("/info")
	boolean add(@RequestBody @Valid ProjectRequest request);

	@ApiOperation("修改项目数据")
	@PutMapping("/info")
	boolean update(@RequestBody @Valid ProjectRequest request);

	@ApiOperation("删除项目数据")
	@DeleteMapping("/{id}")
	boolean delete(@PathVariable("id") Long id);

	@ApiOperation("查询项目详情")
	@GetMapping("/{id}")
	ProjectResponse detail(@PathVariable("id") Long id);
}
