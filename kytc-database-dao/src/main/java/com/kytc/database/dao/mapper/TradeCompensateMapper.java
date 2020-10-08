package com.kytc.database.dao.mapper;

import com.kytc.database.dao.data.TradeCompensateData;

public interface TradeCompensateMapper {

	int deleteByPrimaryKey(Long id);

	int insert(TradeCompensateData record);

	int insertSelective(TradeCompensateData record);

	TradeCompensateData selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TradeCompensateData record);

	int updateByPrimaryKey(TradeCompensateData record);
}