package com.kytc.database.dao.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ColumnDTO implements Serializable {
	private String tableCatalog;
	private String tableSchema;
	private String tableName;
	private String columnName;
	private String ordinalPosition;
	private String columnDefault;
	private String nullable;
	private String dataType;
	private Long characterMaximumLength;
	private Long characterOctetLength;
	private Integer numericPrecision;
	private Integer numericScale;
	private Integer datetimePrecision;
	private String characterSetName;
	private String collationName;
	private String columnType;
	private String columnKey;
	private String extra;
	private String privileges;
	private String columnComment;
	private String javaName;
	private String showComment;
}
