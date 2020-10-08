package com.kytc.database.server.service;

import com.kytc.framework.web.common.BasePageResponse;

import java.util.List;
import java.util.Map;

public interface QueryService {
	/**
	 * @author fisher
	 * @description 查询数据
	 * @date 2018年6月3日下午3:54:41
	 * @param sql
	 * @return
	 */
	BasePageResponse<Map<String,Object>> list(String sql, Integer page, Integer rows);
	/**
	 * @author fisher
	 * @description 获取一条数据
	 * @date 2018年6月3日下午4:02:02
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> listOne(String sql);
}
