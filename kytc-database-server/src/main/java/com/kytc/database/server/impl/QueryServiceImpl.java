package com.kytc.database.server.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kytc.database.dao.mapper.QueryMapper;
import com.kytc.database.server.service.QueryService;
import com.kytc.framework.web.common.BasePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("queryServiceImpl")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class QueryServiceImpl implements QueryService {
	@Autowired
	private final QueryMapper queryMapper;

	@Override
	public BasePageResponse<Map<String, Object>> list(String sql, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		sql = sql.trim().toLowerCase();
		if(sql.endsWith(";")){
			sql = sql.substring(0,sql.length()-1);
		}
		String listSql = sql;
		String countSql = sql;
		Integer start = 0;
		if(rows==null){
			rows = 15;
		}
		if(page!=null){
			start = (page-1)*rows;
		}
		BasePageResponse<Map<String, Object>> pageDTO = new BasePageResponse<Map<String, Object>>();
		System.out.println(listSql);
		if(listSql.length()-listSql.trim().toLowerCase().lastIndexOf(" limit ")>15){
			listSql += " limit #{start},#{pageSize}";
			if(countSql.trim().toLowerCase().contains(" group by ")){
				countSql = "select count(1) from ("+sql+") t";
			}else{
				countSql = "select count(1) "+countSql.substring(countSql.lastIndexOf(" from "));
			}
			List<Map<String, Object>> list = queryMapper.list(listSql,start,rows);
			pageDTO.setRows(list);
			pageDTO.setTotal(queryMapper.count(countSql));
		}else{
			List<Map<String, Object>> list = queryMapper.list(sql,start,rows);
			pageDTO.setRows(list);
			pageDTO.setTotal(Long.valueOf(list.size()));
		}
		return pageDTO;
	}

	@Override
	public List<Map<String, Object>> listOne(String sql) {
		// TODO Auto-generated method stub
		sql = sql.trim().toLowerCase();
		if(sql.length()-sql.lastIndexOf(" limit ")>15){
			sql += " limit 1";
		}else{
			sql = sql.substring(0, sql.lastIndexOf(" limit "))+" limit 1";
		}
		
		
		Map<String, Object> map = queryMapper.listOne(sql);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if (map != null) {
			for (String key : map.keySet()) {
				Object param = map.get(key);
				String type = "String";
				if (param instanceof Integer) {
					type = "Integer";
				} else if (param instanceof String) {
					type = "String";
				} else if (param instanceof Double) {
					type = "Double";
				} else if (param instanceof Float) {
					type = "Float";
				} else if (param instanceof Long) {
					type = "Long";
				} else if (param instanceof Boolean) {
					type = "Boolean";
				} else if (param instanceof Date) {
					type = "Date";
				}
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("field", key);
				map1.put("type", type);
				list.add(map1);
			}
		}
		return list;
	}

}
