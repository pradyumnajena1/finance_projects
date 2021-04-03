package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquityIdentifier;
import com.pd.finance.response.chart.ScreenerEquitySearchResponseLineItem;

import java.util.List;

public interface IScreenerService {

    List<ScreenerEquitySearchResponseLineItem>  findEquity(EquityIdentifier equityIdentifier) throws ServiceException;
}
