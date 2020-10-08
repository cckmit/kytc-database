package com.kytc.database.server.service;

import java.util.List;
import java.util.Map;

import com.kytc.database.request.TableRequest;
import com.kytc.database.response.TableResponse;
import com.kytc.framework.web.common.BasePageResponse;

import javax.servlet.http.HttpServletResponse;

public interface TableService {
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午9:13:40
	 * @description 获取所有的表名
	 * @param database
	 * @return List<String>
	 */
	List<String> list(String database);
	/**
	 * @author fisher 
	 * @date 2018年5月19日下午9:51:43
	 * @description 获取表的详细信息
	 * @param database
	 * @param tableName
	 * @return TableResponse
	 */
	TableResponse detail(String database, String tableName);
	/**
	 * @author fisher 
	 * @date 2018年5月20日上午9:44:14
	 * @description 获取数据集
	 * @param vo
	 * @return PageDTO<Map<String,Object>>
	 */
	BasePageResponse<Map<String,Object>> dataList(TableRequest vo);
	/**
	 * @author fisher 
	 * @date 2018年5月20日下午8:15:53
	 * @description 导出数据操作文件
	 * @param database
	 * @param tableName
	 * @return String
	 */
	void export(String database, String tableName,String pkg,String description,
				  HttpServletResponse response);
	/**
	 * @param database
	 * @param tableName
	 * @param priKey
	 * @param priValue
	 * @return
	 */
	Map<String,Object> dataDetail(String database, String tableName, String priKey, String priValue);
	/**
	 * @author fisher
	 * @description 删除数据
	 * @date 2018年6月16日下午11:08:34
	 * @param database
	 * @param tableName
	 * @param priKey
	 * @param priValue
	 * @return
	 */
	boolean delete(String database, String tableName, String priKey, String priValue);
	/**
	 * @author fisher
	 * @description 添加数据
	 * @date 2018年6月2日下午9:42:01
	 * @param map
	 * @return
	 */
	boolean addData(Map<String, Object> map);
	/**
	 * @author fisher
	 * @description 修改表数据
	 * @date 2018年6月16日下午6:26:52
	 * @param map
	 * @return
	 */
	boolean updateData(Map<String, Object> map);
}
