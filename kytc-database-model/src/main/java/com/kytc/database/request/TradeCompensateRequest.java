package com.kytc.database.request;

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("t_trade_compensate request")
public class TradeCompensateRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty("主键")
	private Long id;
	@ApiModelProperty("发起重试的服务名称")
	private String serviceName;
	@ApiModelProperty("所属业务")
	private String business;
	@ApiModelProperty("业务主键ID")
	private String businessId;
	@ApiModelProperty("请求入参")
	private String request;
	@ApiModelProperty("请求重试的url")
	private String url;
	@ApiModelProperty("请求方式 get post put delete")
	private String method;
	@ApiModelProperty("当前重试次数")
	private Integer currentCount;
	@ApiModelProperty("最大重试次数")
	private Integer maxRetryCount;
	@ApiModelProperty("重试间隔 单位分钟")
	private Integer retryInterval;
	@ApiModelProperty("重试成功的code")
	private String successCode;
	@ApiModelProperty("是否需要重试 true 需要 false 不需要")
	private String needRetry;
	@ApiModelProperty("备注说明")
	private String remark;
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