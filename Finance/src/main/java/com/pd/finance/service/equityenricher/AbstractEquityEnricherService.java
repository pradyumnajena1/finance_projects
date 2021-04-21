package com.pd.finance.service.equityenricher;

import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityAttribute;
import org.joda.time.DateTimeComparator;

import java.util.Date;

public abstract class AbstractEquityEnricherService implements IEquityEnricherService {


    protected boolean isUpdateRequiredForEquityAttribute(EquityAttribute equityAttribute) {
         return  isUpdateRequiredForEquityAttribute(equityAttribute,false);

    }
    protected boolean isUpdateRequiredForEquityAttribute(EquityAttribute equityAttribute,boolean forceUpdate) {
        if(forceUpdate){
            return true;
        }
        if(equityAttribute==null){
            return true;
        }
        boolean isUpdatedOneDayBefore = DateTimeComparator.getDateOnlyInstance().compare(new Date(), equityAttribute.getUpdatedDate()) > 0;
        return isUpdatedOneDayBefore;

    }



}
