package com.kytc.database.server.service;

import com.kytc.database.response.ColumnResponse;

import java.util.List;

public interface ColumnService {
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:16:50
	 * @description 获取表的字段信息
	 * @param database
	 * @param tableName
	 * @return ColumnResponse
	 */
	List<ColumnResponse> list(String database, String tableName);
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:18:00
	 * @description 获取表的字段名称
	 * @param database
	 * @param tableName
	 * @return List<String>
	 */
	List<String> getColumns(String database, String tableName);
}
