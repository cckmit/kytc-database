package com.kytc.database.server.api.impl;

import java.util.List;
import java.util.Map;

import com.kytc.database.server.service.QueryService;
import com.kytc.framework.web.common.BasePageResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("query")
@Api(tags = "查询操作")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class QueryApiImpl {
	private QueryService queryService;
	@PostMapping(value="list")
	public BasePageResponse<Map<String,Object>> list(@RequestParam("sql") String sql,
													 @RequestParam("page") Integer page,
													 @RequestParam("rows") Integer rows){
		return queryService.list(sql, page, rows);
	}
	@GetMapping(value="column")
	public List<Map<String, Object>> column(@RequestParam("sql") String sql){
		return queryService.listOne(sql);
	}
}
