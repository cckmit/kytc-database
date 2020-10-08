package com.kytc.database.server.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kytc.database.dao.mapper.DatabaseMapper;
import com.kytc.database.server.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class DatabaseServiceImpl implements DatabaseService {
	private final DatabaseMapper databaseMapper;
	public List<String> list() {
		// TODO Auto-generated method stub
		return databaseMapper.list();
	}

}
