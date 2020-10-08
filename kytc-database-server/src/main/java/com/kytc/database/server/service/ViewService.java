package com.kytc.database.server.service;

import java.util.List;

import com.kytc.database.response.ViewsResponse;

public interface ViewService {
	/**
	 * @author fisher 
	 * @date 2018年5月19日下午9:54:39
	 * @description 获取试图的列表
	 * @param database
	 * @return List<String>
	 */
	List<String> list(String database);
	/**
	 * @author fisher 
	 * @date 2018年5月19日下午9:54:54
	 * @description 获取试图的详细信息
	 * @param database
	 * @param viewName
	 * @return ViewsResponse
	 */
	ViewsResponse detail(String database, String viewName);
}
