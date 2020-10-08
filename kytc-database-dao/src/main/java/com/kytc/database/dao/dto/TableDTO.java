package com.kytc.database.dao.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TableDTO implements Serializable {
	private String tableCatalog;
	private String tableSchema;
	private String tableName;
	private String tableType;
	private String engine;
	private String version;
	private String rowFormat;
	private String tableRows;
	private Long avgRowLength;
	private Long dataLength;
	private Long maxDataLength;
	private Long indexLength;
	private Long dataFree;
	private Long autoIncrement;
	private Date createTime;
	private Date updateTime;
	private Date checkTime;
	private String tableCollation;
	private String checksum;
	private String createOptions;
	private String tableComment;
}
