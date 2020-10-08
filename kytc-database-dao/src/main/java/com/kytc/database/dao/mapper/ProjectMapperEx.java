package com.kytc.database.dao.mapper;


import com.kytc.database.dao.data.ProjectData;

import java.util.List;

public interface ProjectMapperEx extends ProjectMapper {

	List<ProjectData> listByCondition(String language, String projectKey, String projectType, String projectName, Integer portNum, String databaseName, String gitUrl, String owner, String member, int start, int limit);

	Long countByCondition(String language, String projectKey, String projectType, String projectName, Integer portNum, String databaseName, String gitUrl, String owner, String member);

	Integer getMaxPortNum();
}
