package com.kytc.database.dao.mapper;


import com.kytc.database.dao.data.DatabaseConfigData;

import java.util.List;

public interface DatabaseConfigMapperEx extends DatabaseConfigMapper {

	List<DatabaseConfigData> listByCondition(String databaseName, String databaseDescription, String databaseUrl, String databaseUsername, String databasePassword, Boolean isDeleted, int start, int limit);

	Long countByCondition(String databaseName, String databaseDescription, String databaseUrl, String databaseUsername, String databasePassword, Boolean isDeleted);

	DatabaseConfigData getByDatabaseName(String databaseName);
}
