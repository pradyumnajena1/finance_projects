package com.pd.finance.utils;

import com.pd.finance.converter.EquitySummaryConverter;
import com.pd.finance.model.equity.summary.*;
import com.pd.finance.response.summary.EquitySummary;

public class ConverterUtils {

    public com.pd.finance.model.equity.summary.EquitySummary convert(EquitySummary equitySummary){

        return new EquitySummaryConverter().convert(equitySummary);

    }


}
