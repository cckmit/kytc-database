package com.kytc.database.server.service;

import com.kytc.database.request.ProjectRequest;
import com.kytc.database.response.ProjectResponse;
import com.kytc.framework.web.common.BasePageResponse;


public interface ProjectService {

	boolean add(ProjectRequest request);

	boolean update(ProjectRequest request);

	ProjectResponse detail(Long id);

	boolean delete(Long id);

	BasePageResponse<ProjectResponse> listByCondition(
            ProjectRequest request,
            int page,
            int pageSize);
}
