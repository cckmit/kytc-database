package com.kytc.database.dao.data;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TradeCompensateData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;//主键
	private String serviceName;//发起重试的服务名称
	private String business;//所属业务
	private String businessId;//业务主键ID
	private String request;//请求入参
	private String url;//请求重试的url
	private String method;//请求方式 get post put delete
	private Integer currentCount;//当前重试次数
	private Integer maxRetryCount;//最大重试次数
	private Integer retryInterval;//重试间隔 单位分钟
	private String successCode;//重试成功的code
	private String needRetry;//是否需要重试 true 需要 false 不需要
	private String remark;//备注说明
	private String createdBy;//创建人员
	private Date createdAt;//创建日期
	private String updatedBy;//修改人员
	private Date updatedAt;//修改日期
	private Date lastUpdatedAt;//最后更新时间
}