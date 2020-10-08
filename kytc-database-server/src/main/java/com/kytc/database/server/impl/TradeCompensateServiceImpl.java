package com.kytc.database.server.impl;

import com.kytc.framework.web.common.BasePageResponse;
import com.kytc.framework.web.utils.BeanUtils;
import com.kytc.database.server.service.TradeCompensateService;
import com.kytc.database.request.TradeCompensateRequest;
import com.kytc.database.response.TradeCompensateResponse;
import com.kytc.database.dao.data.TradeCompensateData;
import com.kytc.database.dao.mapper.TradeCompensateMapperEx;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class TradeCompensateServiceImpl implements TradeCompensateService {
	private final TradeCompensateMapperEx tradeCompensateMapperEx;

	@Override
	public void add(TradeCompensateRequest request){
		if( null != request ){
			TradeCompensateData tradeCompensateData = BeanUtils.convert(request, TradeCompensateData.class);
			//tradeCompensateData.setCreatedAt(DateTime.now().toDate());
			tradeCompensateData.setCreatedBy("system");
			//tradeCompensateData.setUpdatedAt(DateTime.now().toDate());
			tradeCompensateData.setUpdatedBy("system");
			this.tradeCompensateMapperEx.insert( tradeCompensateData );
		}
	}

	@Override
	public void update(TradeCompensateRequest request){
		if( null != request ){
			TradeCompensateData tradeCompensateData = BeanUtils.convert(request, TradeCompensateData.class);
			//tradeCompensateData.setUpdatedAt(DateTime.now().toDate());
			tradeCompensateData.setUpdatedBy("system");
			this.tradeCompensateMapperEx.updateByPrimaryKey( tradeCompensateData );
		}
	}

	@Override
	public TradeCompensateResponse detail(Long id){
		TradeCompensateData tradeCompensateData = this.tradeCompensateMapperEx.selectByPrimaryKey( id );
		if( null == tradeCompensateData ){
			return null;
		}
		return BeanUtils.convert(tradeCompensateData,TradeCompensateResponse.class);
	}

	@Override
	public void delete(Long id){
		this.tradeCompensateMapperEx.deleteByPrimaryKey(id);
	}

	@Override
	public BasePageResponse<TradeCompensateResponse> listByCondition(TradeCompensateRequest request,int page, int pageSize){
		BasePageResponse<TradeCompensateResponse> pageResponse = new BasePageResponse<>();
		pageResponse.setRows(this.listByConditionData(request,page, pageSize));
		pageResponse.setTotal(this.countByConditionData(request));
		pageResponse.setPage(page);
		pageResponse.setPageSize(pageSize);
		return pageResponse;
	}

	private List<TradeCompensateResponse> listByConditionData(TradeCompensateRequest request,int page,int pageSize){
		int start = page * pageSize;
		List<TradeCompensateData> list =  this.tradeCompensateMapperEx.listByCondition(request.getServiceName(),
				request.getBusiness(), request.getBusinessId(), request.getRequest(), request.getUrl(),
				request.getMethod(), request.getCurrentCount(), request.getMaxRetryCount(), request.getRetryInterval(),
				request.getSuccessCode(), request.getNeedRetry(), request.getRemark(), start, pageSize);
		return BeanUtils.convert(list,TradeCompensateResponse.class);
	}


	private Long countByConditionData(TradeCompensateRequest request){
		return this.tradeCompensateMapperEx.countByCondition(
				request.getServiceName(),
				request.getBusiness(), request.getBusinessId(), request.getRequest(), request.getUrl(),
				request.getMethod(), request.getCurrentCount(), request.getMaxRetryCount(), request.getRetryInterval(),
				request.getSuccessCode(), request.getNeedRetry(), request.getRemark());
	}
}
