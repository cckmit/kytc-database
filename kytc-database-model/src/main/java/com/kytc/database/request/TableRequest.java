package com.kytc.database.request;

import com.kytc.framework.web.common.BasePageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel("表单字段搜索")
public class TableRequest extends BasePageRequest implements Serializable {
	@ApiModelProperty("数据库")
	@NotNull(message = "数据库不能为空")
	private String database;
	@ApiModelProperty("表")
	@NotNull(message = "表名不能为空")
	private String tableName;
	@ApiModelProperty("字段")
	private String columnName;
	@ApiModelProperty("值")
	private String columnValue;
}
