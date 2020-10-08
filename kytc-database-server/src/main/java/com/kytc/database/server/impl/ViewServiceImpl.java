package com.kytc.database.server.impl;

import java.util.List;

import com.kytc.database.dao.dto.ViewsDTO;
import com.kytc.database.dao.mapper.ViewsMapper;
import com.kytc.database.response.ViewsResponse;
import com.kytc.database.server.service.ViewService;
import com.kytc.framework.web.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class ViewServiceImpl implements ViewService {
	private final ViewsMapper viewsMapper;
	@Override
	public List<String> list(String database) {
		// TODO Auto-generated method stub
		return viewsMapper.list(database);
	}

	@Override
	public ViewsResponse detail(String database, String viewName) {
		// TODO Auto-generated method stub
		ViewsDTO viewsDTO = viewsMapper.detail(database, viewName);
		return BeanUtils.convert(viewsDTO,ViewsResponse.class);
	}

}
