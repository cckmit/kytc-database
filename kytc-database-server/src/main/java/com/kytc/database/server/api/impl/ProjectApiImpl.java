package com.kytc.database.server.api.impl;

import com.kytc.database.request.ProjectRequest;
import com.kytc.database.response.ProjectResponse;
import com.kytc.database.api.ProjectApi;
import com.kytc.database.server.service.ProjectService;
import com.kytc.framework.web.common.BasePageResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ProjectApiImpl implements ProjectApi {
	private final ProjectService projectService;

	@Override
	public BasePageResponse<ProjectResponse> listByCondition(
		@RequestBody @Valid ProjectRequest request,
		@RequestParam("index")int page,
		@RequestParam("pageSize")int pageSize){
			return this.projectService.listByCondition( request,page, pageSize);
	}

	@Override
	public boolean add(@RequestBody @Valid ProjectRequest request) {
		return this.projectService.add(request);
	}

	@Override
	public boolean update(@RequestBody @Valid ProjectRequest request) {
		return this.projectService.update(request);
	}

	@Override
	public boolean delete(@PathVariable("id") Long id) {
		return this.projectService.delete(id);
	}

	@Override
	public ProjectResponse detail(@PathVariable("id") Long id) {
		return this.projectService.detail(id);
	}
}
