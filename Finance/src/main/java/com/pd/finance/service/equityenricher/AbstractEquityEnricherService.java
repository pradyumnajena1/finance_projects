package com.pd.finance.service.equityenricher;

import com.pd.finance.model.EquityAttribute;
import org.joda.time.DateTimeComparator;

import java.util.Date;

public abstract class AbstractEquityEnricherService implements IEquityEnricherService {

    protected boolean isUpdateRequiredForEquityAttribute(EquityAttribute equityAttribute) {
        if(equityAttribute==null){
            return true;
        }
        return DateTimeComparator.getDateOnlyInstance().compare(new Date(), equityAttribute.getUpdatedDate()) > 0;

    }
}
