package com.kytc.database.server.api.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.kytc.database.request.TableRequest;
import com.kytc.database.response.TableResponse;
import com.kytc.database.server.service.TableService;
import com.kytc.framework.web.common.BasePageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("table")
@Api(tags = "表操作")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class TableApiImpl {
	private final TableService tableService;
	@GetMapping(value="list")
	public List<String> list(String database){
		return tableService.list(database);
	}
	@GetMapping(value="info")
	public TableResponse info(@RequestParam("database") String database, @RequestParam("tableName")String tableName){
		return tableService.detail(database, tableName);
	}
	@PostMapping(value="list")
	@ApiOperation("查询表数据")
	public BasePageResponse<Map<String, Object>> list(@RequestBody @Valid TableRequest request){
		return tableService.dataList(request);
	}
	@GetMapping(value="data")
	@ApiOperation("主键查询数据")
	public Map<String,Object> data(
			@RequestParam("database") String database,
			@RequestParam("tableName") String tableName,
			@RequestParam("priKey") String priKey,
			@RequestParam("priValue") String priValue){
		return tableService.dataDetail(database, tableName, priKey, priValue);
	}
	@PostMapping(value="update")
	@ApiOperation("更新数据")
	public boolean update(HttpServletRequest request){
		Map<String,Object> map = getRequestMap(request);
		return tableService.updateData(map);
	}
	private Map<String,Object> getRequestMap(HttpServletRequest request){
		Enumeration<String> keyEnum = request.getParameterNames();
		Map<String,Object> map = new HashMap<String,Object>();
		while(keyEnum.hasMoreElements()){
			String key = keyEnum.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}
	@PostMapping(value="add")
	public boolean add(HttpServletRequest request){
		Map<String,Object> map = getRequestMap(request);
		return tableService.addData(map);
	}
	@PostMapping(value="delete")
	public boolean delete(String database, String tableName, String priKey, String priValue){
		System.out.println(database+"   "+tableName+"   "+priKey+"  ==  "+priValue);
		return tableService.delete(database, tableName, priKey, priValue);
	}
	@ApiOperation(value = "表导出下载信息")
	@GetMapping(value="export",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void export(@RequestParam("database") String database,
						 @RequestParam("tableName") String tableName,
						 @RequestParam("pkg") String pkg,
						 @RequestParam("description")String description,
						 HttpServletResponse response){
		response.setHeader("Content-disposition", "attachment; filename=" + tableName+".zip");
		response.setContentType("application/octet-stream");
		tableService.export(database, tableName,pkg,description,response);
		response.setHeader("Content-disposition", "attachment; filename=" + tableName+".zip");
		response.setHeader("Content-Disposition", "attachment; filename=" + tableName+".zip");
		response.setContentType("application/force-download");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Expose-Headers"," Content-Disposition");
	}
}
