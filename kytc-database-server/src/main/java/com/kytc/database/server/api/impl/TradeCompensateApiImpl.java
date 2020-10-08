package com.kytc.database.server.api.impl;

import com.kytc.database.request.TradeCompensateRequest;
import com.kytc.database.response.TradeCompensateResponse;
import com.kytc.database.api.TradeCompensateApi;
import com.kytc.database.server.service.TradeCompensateService;

import com.kytc.framework.web.common.BasePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class TradeCompensateApiImpl implements TradeCompensateApi {
	private final TradeCompensateService tradeCompensateService;

	@Override
	public BasePageResponse<TradeCompensateResponse> listByCondition(
		@RequestBody TradeCompensateRequest request,
		@RequestParam("page")int page,
		@RequestParam("pageSize")int pageSize){
			return this.tradeCompensateService.listByCondition( request,page, pageSize);
	}

	@Override
	public void add(@RequestBody @Valid TradeCompensateRequest request) {
		this.tradeCompensateService.add(request);
	}

	@Override
	public void update(@RequestBody @Valid TradeCompensateRequest request) {
		this.tradeCompensateService.update(request);
	}

	@Override
	public void delete(@PathVariable("id") Long id) {
		this.tradeCompensateService.delete(id);
	}

	@Override
	public TradeCompensateResponse detail(@PathVariable("id") Long id) {
		return this.tradeCompensateService.detail(id);
	}
}