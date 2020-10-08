package com.kytc.database.dao.mapper;

import com.kytc.database.dao.data.ProjectData;

public interface ProjectMapper {

	int deleteByPrimaryKey(Long id);

	int insert(ProjectData record);

	int insertSelective(ProjectData record);

	ProjectData selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(ProjectData record);

	int updateByPrimaryKey(ProjectData record);
}
