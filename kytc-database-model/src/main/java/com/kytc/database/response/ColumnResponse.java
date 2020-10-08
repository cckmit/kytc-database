package com.kytc.database.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("字段属性")
public class ColumnResponse implements Serializable {
	@ApiModelProperty("表限定符")
	private String tableCatalog;
	@ApiModelProperty("表所有者（对于schema的名称）")
	private String tableSchema;
	@ApiModelProperty("表名")
	private String tableName;
	@ApiModelProperty("列名")
	private String columnName;
	@ApiModelProperty("列标识号")
	private String ordinalPosition;
	@ApiModelProperty("列的默认值\n")
	private String columnDefault;
	@ApiModelProperty("列的为空性。如果列允许 null，那么该列返回 yes。否则，返回 no")
	private String nullable;
	@ApiModelProperty("系统提供的数据类型")
	private String dataType;
	@ApiModelProperty("以字符为单位的最大长度，适于二进制数据、字符数据，或者文本和图像数据。否则，返回 null。有关更多信息")
	private Long characterMaximumLength;
	@ApiModelProperty("以字节为单位的最大长度，适于二进制数据、字符数据，或者文本和图像数据。否则，返回 null")
	private Long characterOctetLength;
	@ApiModelProperty("近似数字数据、精确数字数据、整型数据或货币数据的精度。否则，返回 null")
	private Integer numericPrecision;
	@ApiModelProperty("对于数字列，数字刻度")
	private Integer numericScale;
	@ApiModelProperty("对于时间列，分数秒精度")
	private Integer datetimePrecision;
	@ApiModelProperty("对于字符串列，字符集名称")
	private String characterSetName;
	@ApiModelProperty("对于字符串列，归类名称")
	private String collationName;
	@ApiModelProperty("列数据类型。该DATA_TYPE值仅是类型名称，没有其他信息。该COLUMN_TYPE 值包含类型名称以及可能的其他信息，例如精度或长度")
	private String columnType;
	@ApiModelProperty("索引")
	private String columnKey;
	@ApiModelProperty("扩展")
	private String extra;
	@ApiModelProperty("字段权限")
	private String privileges;
	@ApiModelProperty("字段注释")
	private String columnComment;
}
