package com.kytc.database.api;

import com.kytc.database.request.TradeCompensateRequest;
import com.kytc.database.response.TradeCompensateResponse;

import com.kytc.framework.web.common.BasePageResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/trade/compensate")
public interface TradeCompensateApi {

	@PostMapping("/infos")
	BasePageResponse<TradeCompensateResponse> listByCondition(
		@RequestBody TradeCompensateRequest request,
		@RequestParam("page")int page,
		@RequestParam("pageSize")int pageSize);

	@PostMapping("/info")
	void add(@RequestBody @Valid TradeCompensateRequest request);

	@PutMapping("/info")
	void update(@RequestBody @Valid TradeCompensateRequest request);

	@DeleteMapping("/{id}")
	void delete(@PathVariable("id")Long id);

	@GetMapping("/{id}")
	TradeCompensateResponse detail(@PathVariable("id")Long id);
}