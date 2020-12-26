package com.kytc.database.server.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.kytc.database.dao.dto.TableDTO;
import com.kytc.database.dao.mapper.TableMapper;
import com.kytc.database.dao.vo.TableVO;
import com.kytc.database.request.TableRequest;
import com.kytc.database.response.ColumnResponse;
import com.kytc.database.response.TableResponse;
import com.kytc.database.server.dto.AnalyzerDTO;
import com.kytc.database.server.dto.ColumnIndexDTO;
import com.kytc.database.server.helper.AnalyzerHelper;
import com.kytc.database.server.service.ColumnService;
import com.kytc.database.server.service.DynamicService;
import com.kytc.database.server.service.TableService;
import com.kytc.database.server.service.ayalyzer.Analyzer;
import com.kytc.database.server.utils.DatabaseUtils;
import com.kytc.framework.common.utils.TxtUtils;
import com.kytc.framework.common.utils.ZipUtils;
import com.kytc.framework.web.common.BasePageResponse;
import com.kytc.framework.web.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class TableServiceImpl implements TableService {
	private final TableMapper tableMapper;
	private final AnalyzerHelper analyzerHelper;
	private final ColumnService columnService;
	private final DynamicService dynamicService;
	@Value("${database.path:/Users/hezhitong/Documents/database/file}")
	private String filePath;
	@Override
	public List<String> list(String database) {
		// TODO Auto-generated method stub
		return tableMapper.list(database);
	}
	@Override
	public TableResponse detail(String database, String tableName) {
		// TODO Auto-generated method stub
		TableDTO tableDTO = tableMapper.detail(database, tableName);
		return BeanUtils.convert(tableDTO,TableResponse.class);
	}
	@Override
	public BasePageResponse<Map<String, Object>> dataList(TableRequest request) {
		// TODO Auto-generated method stub
		request.init();
		TableVO vo = BeanUtils.convert(request, TableVO.class);
		BasePageResponse<Map<String, Object>> page = new BasePageResponse<Map<String, Object>>();
		page.setTotal( tableMapper.dataCount(vo) );
		page.setRows( tableMapper.dataList(vo) );
		return page;
	}
	private Map<Boolean,Map<String,List<ColumnIndexDTO>>> convert(List<ColumnIndexDTO> columnIndexDTOList){
		Map<Boolean,Map<String,List<ColumnIndexDTO>>> columnMap = new HashMap<>();
		if(CollectionUtils.isEmpty(columnIndexDTOList)){
			return columnMap;
		}
		for( ColumnIndexDTO columnIndexDTO : columnIndexDTOList ){
			if( !columnMap.containsKey(columnIndexDTO.isNon_unique()) ){
				columnMap.put(columnIndexDTO.isNon_unique(),new HashMap<>());
			}
			Map<String,List<ColumnIndexDTO>> map = columnMap.get(columnIndexDTO.isNon_unique());
			if( !map.containsKey(columnIndexDTO.getKey_name()) ){
				map.put(columnIndexDTO.getKey_name(),new ArrayList<>());
			}
			map.get(columnIndexDTO.getKey_name()).add(columnIndexDTO);
		}
		return columnMap;
	}
	@Override
	public void export(String database, String tableName,String pkg,String description,
						 HttpServletResponse response) {
		List<ColumnResponse> list = this.columnService.list(database, tableName);
		this.initColumn(list);
		List<Map<String,Object>> indexColums = this.dynamicService.select(database,"show index from "+tableName);
		List<ColumnIndexDTO> columnIndexDTOList = JSON.parseArray(JSON.toJSONString(indexColums), ColumnIndexDTO.class);
		for(Analyzer analyzer : analyzerHelper.getAnalyzer()){
			AnalyzerDTO analyzerDTO = AnalyzerDTO.builder().pkg(pkg).tableName(tableName).columnResponses(list).columnMap(this.convert(columnIndexDTOList)).description(description).build();
			List<String> res = analyzer.analyzer(analyzerDTO);
			String path = analyzer.getFilePath(pkg,tableName);
			String name = analyzer.getFileName(tableName);
			String fileName = filePath+File.separator+"database"+ File.separator+path;
			if(!StringUtils.isEmpty(analyzer.getPackage())){
				fileName = fileName.concat(File.separator+analyzer.getPackage());
			}
			fileName = fileName.concat(File.separator).concat(name);
			TxtUtils.getInstance().write(fileName,res);
		}
		try{
			String path = filePath+File.separator+"database"+ File.separator+pkg.replaceAll("\\.",File.separator+File.separator);
			log.info("path:{}",path);
			ZipUtils.toZip(path,
					response.getOutputStream(),true);
		}catch (Exception e){
		}
	}

	@Override
	public Map<String, Object> dataDetail(String database,
			String tableName, String priKey, String priValue) {
		// TODO Auto-generated method stub
		return tableMapper.dataDetail(database, tableName, priKey, priValue);
	}
	@Override
	public boolean addData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return tableMapper.addData(map);
	}
	@Override
	public boolean updateData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return tableMapper.updateData(map);
	}
	@Override
	public boolean delete(String database, String tableName,
			String priKey, String priValue) {
		// TODO Auto-generated method stub
		return tableMapper.deleteData(database, tableName, priKey, priValue);
	}

	private void initColumn(List<ColumnResponse> list){
		if(CollectionUtils.isEmpty(list)){
			return;
		}
		for(ColumnResponse response:list){
			response.setJavaType(DatabaseUtils.getJavaType(response.getColumnType()));
			response.setJavaName(DatabaseUtils.getJavaName(response.getColumnName()));
		}
	}
}
