package com.kytc.database.server.impl;

import java.util.List;

import com.kytc.database.dao.dto.ColumnDTO;
import com.kytc.database.dao.mapper.ColumnMapper;
import com.kytc.database.response.ColumnResponse;
import com.kytc.database.server.service.ColumnService;
import com.kytc.framework.web.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ColumnServiceImpl implements ColumnService {
	private final ColumnMapper columnMapper;
	@Override
	public List<ColumnResponse> list(String database, String tableName) {
		// TODO Auto-generated method stub
		List<ColumnDTO> list = columnMapper.list(database, tableName);
		return BeanUtils.convert(list, ColumnResponse.class);
	}

	@Override
	public List<String> getColumns(String database, String tableName) {
		// TODO Auto-generated method stub
		return columnMapper.getColumn(database, tableName);
	}

}
