package com.kytc.database.server.impl;

import com.kytc.framework.web.common.BasePageResponse;
import com.kytc.framework.web.utils.BeanUtils;
import com.kytc.database.server.service.DatabaseConfigService;
import com.kytc.database.request.DatabaseConfigRequest;
import com.kytc.database.response.DatabaseConfigResponse;
import com.kytc.database.dao.data.DatabaseConfigData;
import com.kytc.database.dao.mapper.DatabaseConfigMapperEx;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class DatabaseConfigServiceImpl implements DatabaseConfigService {
	private final DatabaseConfigMapperEx databaseConfigMapperEx;

	@Override
	public boolean add(DatabaseConfigRequest request){
		if( null != request ){
			DatabaseConfigData databaseConfigData = BeanUtils.convert(request, DatabaseConfigData.class);
			databaseConfigData.setCreatedAt(new Date());
			databaseConfigData.setUpdatedAt(new Date());
			return this.databaseConfigMapperEx.insert(databaseConfigData)>0;
		}
		return false;
	}

	@Override
	public boolean update(DatabaseConfigRequest request){
		if( null != request ){
			DatabaseConfigData databaseConfigData = BeanUtils.convert(request, DatabaseConfigData.class);
			databaseConfigData.setUpdatedAt(new Date());
			return this.databaseConfigMapperEx.updateByPrimaryKey(databaseConfigData)>0;
		}
		return false;
	}

	@Override
	public DatabaseConfigResponse detail(Long id){
		DatabaseConfigData databaseConfigData = this.databaseConfigMapperEx.selectByPrimaryKey( id );
		if( null == databaseConfigData ){
			return null;
		}
		return BeanUtils.convert(databaseConfigData,DatabaseConfigResponse.class);
	}

	@Override
	public boolean delete(Long id){
		DatabaseConfigData databaseConfigData = new DatabaseConfigData();
		databaseConfigData.setId(id);
		databaseConfigData.setIsDeleted(true);
		databaseConfigData.setUpdatedAt(new Date());
		return this.databaseConfigMapperEx.updateByPrimaryKeySelective(databaseConfigData)>0;
	}

	@Override
	public BasePageResponse<DatabaseConfigResponse> listByCondition(DatabaseConfigRequest request,int page, int pageSize){
		BasePageResponse<DatabaseConfigResponse> pageResponse = new BasePageResponse<>();
		pageResponse.setRows(this.listByConditionData(request,page, pageSize));
		pageResponse.setTotal(this.countByConditionData(request));
		pageResponse.setPage(page);
		pageResponse.setPageSize(pageSize);
		return pageResponse;
	}

	private List<DatabaseConfigResponse> listByConditionData(DatabaseConfigRequest request,int page,int pageSize){
		int start = page * pageSize;
		List<DatabaseConfigData> list =  this.databaseConfigMapperEx.listByCondition(request.getDatabaseName(), request.getDatabaseDescription(), request.getDatabaseUrl(), request.getDatabaseUsername(), request.getDatabasePassword(), request.getIsDeleted(), start, pageSize);
		return BeanUtils.convert(list,DatabaseConfigResponse.class);
	}


	private Long countByConditionData(DatabaseConfigRequest request){
		return this.databaseConfigMapperEx.countByCondition(request.getDatabaseName(), request.getDatabaseDescription(), request.getDatabaseUrl(), request.getDatabaseUsername(), request.getDatabasePassword(), request.getIsDeleted());
	}
}
