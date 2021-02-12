package com.pd.finance.model;

import com.pd.finance.htmlscrapper.equity.EquityTechnicalDetailsFactory;
import com.pd.finance.utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class TechAnalysisSummaryValue {
    private static final Logger logger = LoggerFactory.getLogger(TechAnalysisSummaryValue.class);
    private String trend;
    private String value;

    private int bullish;
    private int neutral;
    private int bearish;

    public TechAnalysisSummaryValue() {
    }

    public TechAnalysisSummaryValue(String trend, String value) {
        this.trend = trend;
        this.value = value;
    }

    private void translate(){
        try {
            if(StringUtils.isNotBlank(value)){
                Scanner scanner = new Scanner(value);
                if(scanner.hasNext()){
                    bullish = CommonUtils.extractIntegerFromText(scanner.next()).intValue();
                }
                if(scanner.hasNext()){
                    neutral = CommonUtils.extractIntegerFromText(scanner.next()).intValue();
                }
                if(scanner.hasNext()){
                    bearish =  CommonUtils.extractIntegerFromText(scanner.next()).intValue();
                }


            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        translate();
    }

    public int getBullish() {
        return bullish;
    }

    public int getNeutral() {
        return neutral;
    }

    public int getBearish() {
        return bearish;
    }

    public void setBullish(int bullish) {
        this.bullish = bullish;
    }

    public void setNeutral(int neutral) {
        this.neutral = neutral;
    }

    public void setBearish(int bearish) {
        this.bearish = bearish;
    }
}
