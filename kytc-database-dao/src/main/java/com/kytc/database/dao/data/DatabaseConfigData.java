package com.kytc.database.dao.data;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//动态数据库配置
@Data
public class DatabaseConfigData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String databaseName;//数据库名称
	private String databaseDescription;//数据库描述
	private String databaseUrl;//数据库连接地址
	private String databaseUsername;//数据库用户名
	private String databasePassword;//数据库密码
	private Boolean isDeleted;//是否删除
	private String createdBy;//创建人员
	private Date createdAt;//创建日期
	private String updatedBy;//修改人员
	private Date updatedAt;//修改日期
	private Date lastUpdatedAt;//最后更新时间
}
