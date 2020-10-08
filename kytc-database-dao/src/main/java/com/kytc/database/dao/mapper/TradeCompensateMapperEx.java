package com.kytc.database.dao.mapper;


import com.kytc.database.dao.data.TradeCompensateData;

import java.util.List;


public interface TradeCompensateMapperEx extends TradeCompensateMapper {

	List<TradeCompensateData> listByCondition(String serviceName, String business, String businessId, String request, String url, String method, Integer currentCount, Integer maxRetryCount, Integer retryInterval, String successCode, String needRetry, String remark, int start, int limit);

	Long countByCondition(String serviceName, String business, String businessId, String request, String url, String method, Integer currentCount, Integer maxRetryCount, Integer retryInterval, String successCode, String needRetry, String remark);
}