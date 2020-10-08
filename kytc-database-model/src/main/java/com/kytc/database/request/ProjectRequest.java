package com.kytc.database.request;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("项目 request")
public class ProjectRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("主键")
	private Long id;
	@ApiModelProperty("开发语言 JAVA PYTHON NET REACT ")
	private String language;
	@ApiModelProperty("工程key")
	private String projectKey;
	@ApiModelProperty("工程类型 SDK API HUB")
	private String projectType;
	@ApiModelProperty("工程名称")
	private String projectName;
	@ApiModelProperty("端口号")
	private Integer portNum;
	@ApiModelProperty("数据库")
	private String databaseName;
	@ApiModelProperty("仓库地址")
	private String gitUrl;
	@ApiModelProperty("项目负责人")
	private String owner;
	@ApiModelProperty("成员")
	private String member;
	@ApiModelProperty("创建人员")
	private String createdBy;
	@ApiModelProperty("创建日期")
	private Date createdAt;
	@ApiModelProperty("修改人员")
	private String updatedBy;
	@ApiModelProperty("修改日期")
	private Date updatedAt;
	@ApiModelProperty("最后更新时间")
	private Date lastUpdatedAt;
}
