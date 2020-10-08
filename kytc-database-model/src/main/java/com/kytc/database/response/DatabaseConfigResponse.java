package com.kytc.database.response;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("动态数据库配置 response")
public class DatabaseConfigResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("主键")
	private Long id;
	@ApiModelProperty("数据库名称")
	private String databaseName;
	@ApiModelProperty("数据库描述")
	private String databaseDescription;
	@ApiModelProperty("数据库连接地址")
	private String databaseUrl;
	@ApiModelProperty("数据库用户名")
	private String databaseUsername;
	@ApiModelProperty("数据库密码")
	private String databasePassword;
	@ApiModelProperty("是否删除")
	private Boolean isDeleted;
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
