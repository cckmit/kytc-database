package com.kytc.database.dao.mapper;

import com.kytc.database.dao.data.DatabaseConfigData;

public interface DatabaseConfigMapper {

	int deleteByPrimaryKey(Long id);

	int insert(DatabaseConfigData record);

	int insertSelective(DatabaseConfigData record);

	DatabaseConfigData selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(DatabaseConfigData record);

	int updateByPrimaryKey(DatabaseConfigData record);
}
