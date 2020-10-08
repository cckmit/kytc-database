package com.kytc.database.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QueryMapper {
	/**
	 * @author fisher
	 * @description 根据sql查询数据
	 * @date 2018年6月3日下午3:51:49
	 * @param sql
	 * @return
	 */
	List<Map<String,Object>> list(@Param("sql") String sql, @Param("start") Integer start, @Param("pageSize") Integer pageSize);
	/**
	 * @author fisher
	 * @description 查询数据数量
	 * @date 2018年6月3日下午5:39:48
	 * @param sql
	 * @return
	 */
	Long count(@Param("sql") String sql);
	/**
	 * @author fisher
	 * @description 获取表头数据
	 * @date 2018年6月3日下午4:03:44
	 * @param sql
	 * @return
	 */
	Map<String,Object> listOne(@Param("sql") String sql);
}
