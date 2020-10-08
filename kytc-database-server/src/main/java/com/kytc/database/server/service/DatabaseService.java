package com.kytc.database.server.service;

import java.util.List;

public interface DatabaseService {
	/**
	 * @author fisher 
	 * @date 2018年5月13日下午4:02:06
	 * @description 获取数据库列表
	 * @return ResultDTO<List<String>>
	 */
	List<String> list();
}
