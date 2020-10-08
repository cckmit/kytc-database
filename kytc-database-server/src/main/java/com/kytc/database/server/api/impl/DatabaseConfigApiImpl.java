package com.kytc.database.server.api.impl;

import com.kytc.database.request.DatabaseConfigRequest;
import com.kytc.database.response.DatabaseConfigResponse;
import com.kytc.database.api.DatabaseConfigApi;
import com.kytc.database.server.service.DatabaseConfigService;
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
public class DatabaseConfigApiImpl implements DatabaseConfigApi {
	private final DatabaseConfigService databaseConfigService;

	@Override
	public BasePageResponse<DatabaseConfigResponse> listByCondition(
		@RequestBody @Valid DatabaseConfigRequest request,
		@RequestParam("index")int page,
		@RequestParam("pageSize")int pageSize){
			return this.databaseConfigService.listByCondition( request,page, pageSize);
	}

	@Override
	public boolean add(@RequestBody @Valid DatabaseConfigRequest request) {
		return this.databaseConfigService.add(request);
	}

	@Override
	public boolean update(@RequestBody @Valid DatabaseConfigRequest request) {
		return this.databaseConfigService.update(request);
	}

	@Override
	public boolean delete(@PathVariable("id") Long id) {
		return this.databaseConfigService.delete(id);
	}

	@Override
	public DatabaseConfigResponse detail(@PathVariable("id") Long id) {
		return this.databaseConfigService.detail(id);
	}
}
