package com.pd.finance.infra;

import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.request.CreateStockExchangeDetailsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface ObjectConverter extends IObjectConverter{
    ObjectConverter MAPPER = Mappers.getMapper(ObjectConverter.class);

    @Override

    public EquityStockExchangeDetails convert(CreateStockExchangeDetailsRequest exchangeDetailsRequest) ;
}
