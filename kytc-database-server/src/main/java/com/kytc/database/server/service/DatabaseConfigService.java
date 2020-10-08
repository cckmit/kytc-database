package com.kytc.database.server.service;

import com.kytc.database.request.DatabaseConfigRequest;
import com.kytc.database.response.DatabaseConfigResponse;
import com.kytc.framework.web.common.BasePageResponse;


public interface DatabaseConfigService {

	boolean add(DatabaseConfigRequest request);

	boolean update(DatabaseConfigRequest request);

	DatabaseConfigResponse detail(Long id);

	boolean delete(Long id);

	BasePageResponse<DatabaseConfigResponse> listByCondition(
            DatabaseConfigRequest request,
            int page,
            int pageSize);
}
