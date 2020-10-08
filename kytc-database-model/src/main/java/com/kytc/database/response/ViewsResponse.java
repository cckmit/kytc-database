package com.kytc.database.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("视图属性")
public class ViewsResponse implements Serializable {
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
