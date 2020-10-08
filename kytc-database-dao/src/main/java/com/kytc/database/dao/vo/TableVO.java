package com.kytc.database.dao.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TableVO implements Serializable {
	private String database;
	private String tableName;
	private String columnName;
	private String columnValue;
}
