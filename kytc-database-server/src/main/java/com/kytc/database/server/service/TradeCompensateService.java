package com.kytc.database.server.service;

import com.kytc.database.request.TradeCompensateRequest;
import com.kytc.database.response.TradeCompensateResponse;
import com.kytc.framework.web.common.BasePageResponse;


public interface TradeCompensateService {

	void add(TradeCompensateRequest request);

	void update(TradeCompensateRequest request);

	TradeCompensateResponse detail(Long id);

	void delete(Long id);

	BasePageResponse<TradeCompensateResponse> listByCondition(
		TradeCompensateRequest request,
		int page,
		int pageSize);
}