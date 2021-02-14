package com.pd.finance.model;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EquityHistoricalData {



    @Nonnull
    private Date startDate;
    @Nonnull
    private Date endDate;
    @Nonnull
    private List<EquityHistoricalDataLineItem> lineItems;

    public EquityHistoricalData(@Nonnull Date startDate,@Nonnull Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        lineItems = new ArrayList<>();
    }


    public List<EquityHistoricalDataLineItem> getLineItems() {
        return new ArrayList<>(lineItems);
    }

    public void addLineItems(@Nonnull List<EquityHistoricalDataLineItem> lineItems) {
        for (EquityHistoricalDataLineItem lineItem:lineItems){
            addLineItem(lineItem);
        }

    }

    public void addLineItem(@Nonnull EquityHistoricalDataLineItem lineItem){
        Date lineItemDate = lineItem.getDate();

        if(startDate.after(lineItemDate)){
            throw new IllegalArgumentException(MessageFormat.format("line items must be between {} and {}",startDate,endDate));
        }
        if(endDate.before(lineItemDate)){
            throw new IllegalArgumentException(MessageFormat.format("line items must be between {} and {}",startDate,endDate));
        }
        lineItems.add(lineItem);

    }


}
