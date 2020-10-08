package com.kytc.database.dao.mapper;

import java.util.List;

import com.kytc.database.dao.dto.ColumnDTO;
import org.apache.ibatis.annotations.Param;

public interface ColumnMapper {
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午6:51:26
	 * @description 获取字段详细信息
	 * @param database
	 * @param tableName
	 * @return List<ColumnDTO>
	 */
	List<ColumnDTO> list(@Param("database") String database, @Param("tableName") String tableName);
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午6:51:04
	 * @description 获取字段名称
	 * @param database 数据库名
	 * @param tableName 表明
	 * @return List<String>
	 */
	List<String> getColumn(@Param("database") String database, @Param("tableName") String tableName);
}
