package com.kytc.database.dao.data;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//项目
@Data
public class ProjectData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String language;//开发语言 JAVA PYTHON NET REACT 
	private String projectKey;//工程key
	private String projectType;//工程类型 SDK API HUB
	private String projectName;//工程名称
	private Integer portNum;//端口号
	private String databaseName;//数据库
	private String gitUrl;//仓库地址
	private String owner;//项目负责人
	private String member;//成员
	private String createdBy;//创建人员
	private Date createdAt;//创建日期
	private String updatedBy;//修改人员
	private Date updatedAt;//修改日期
	private Date lastUpdatedAt;//最后更新时间
}
