package com.kytc.database.dao.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ViewsDTO implements Serializable {
	private String tableCatalog;
	private String tableSchema;
	private String tableName;
	private String viewDefinition;
	private String checkOption;
	private String updatable;
	private String definer;
	private String securityType;
	private String characterSetClient;
	private String collationConnection;
}
