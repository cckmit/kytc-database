package com.kytc.database.server.impl;

import com.kytc.framework.web.common.BasePageResponse;
import com.kytc.framework.web.utils.BeanUtils;
import com.kytc.database.server.service.ProjectService;
import com.kytc.database.request.ProjectRequest;
import com.kytc.database.response.ProjectResponse;
import com.kytc.database.dao.data.ProjectData;
import com.kytc.database.dao.mapper.ProjectMapperEx;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ProjectServiceImpl implements ProjectService {
	private final ProjectMapperEx projectMapperEx;

	@Override
	public boolean add(ProjectRequest request){
		if( null != request ){
			Integer maxPortNum = this.projectMapperEx.getMaxPortNum();
			if( null == maxPortNum ){
				maxPortNum = 990;
			}
			ProjectData projectData = BeanUtils.convert(request, ProjectData.class);
			projectData.setPortNum(maxPortNum+10);
			projectData.setCreatedAt(new Date());
			projectData.setCreatedBy("system");
			projectData.setUpdatedAt(new Date());
			projectData.setUpdatedBy("system");
			return this.projectMapperEx.insert( projectData )>0;
		}
		return false;
	}

	@Override
	public boolean update(ProjectRequest request){
		if( null != request ){
			ProjectData projectData = BeanUtils.convert(request, ProjectData.class);
			projectData.setUpdatedAt(new Date());
			projectData.setUpdatedBy("system");
			return this.projectMapperEx.updateByPrimaryKey( projectData )>0;
		}
		return false;
	}

	@Override
	public ProjectResponse detail(Long id){
		ProjectData projectData = this.projectMapperEx.selectByPrimaryKey( id );
		if( null == projectData ){
			return null;
		}
		return BeanUtils.convert(projectData,ProjectResponse.class);
	}

	@Override
	public boolean delete(Long id){
		return this.projectMapperEx.deleteByPrimaryKey(id)>0;
	}

	@Override
	public BasePageResponse<ProjectResponse> listByCondition(ProjectRequest request,int page, int pageSize){
		BasePageResponse<ProjectResponse> pageResponse = new BasePageResponse<>();
		pageResponse.setRows(this.listByConditionData(request,page, pageSize));
		pageResponse.setTotal(this.countByConditionData(request));
		pageResponse.setPage(page);
		pageResponse.setPageSize(pageSize);
		return pageResponse;
	}

	private List<ProjectResponse> listByConditionData(ProjectRequest request,int page,int pageSize){
		int start = page * pageSize;
		List<ProjectData> list =  this.projectMapperEx.listByCondition(request.getLanguage(), request.getProjectKey(), request.getProjectType(), request.getProjectName(), request.getPortNum(), request.getDatabaseName(), request.getGitUrl(), request.getOwner(), request.getMember(), start, pageSize);
		return BeanUtils.convert(list,ProjectResponse.class);
	}


	private Long countByConditionData(ProjectRequest request){
		return this.projectMapperEx.countByCondition(request.getLanguage(), request.getProjectKey(), request.getProjectType(), request.getProjectName(), request.getPortNum(), request.getDatabaseName(), request.getGitUrl(), request.getOwner(), request.getMember());
	}
}
