package com.pd.finance.utils;

import com.pd.finance.htmlscrapper.marketgainer.MarketGainerEquityFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");

    public static BigDecimal extractDecimalFromText(String textValue) {
        try {

            textValue = textValue.replace(",","");
            textValue = textValue.trim();
            return new BigDecimal( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractDecimalFromPerformanceCell exec failed",ex);
            return BigDecimal.ZERO;
        }
    }
    public static BigInteger extractIntegerFromText(String textValue) {
        try {

            textValue = textValue.replace(",","");
            textValue = textValue.trim();
            return new BigInteger( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractDecimalFromPerformanceCell exec failed",ex);
            return BigInteger.ZERO;
        }
    }



    public static Date extractDateFromText(String textValue) {
        try {

            textValue = textValue.trim();
            return dateFormat.parse( textValue);
        } catch (ParseException ex) {
            logger.error("extractDateFromPerformanceCell exec failed",ex);
            return null;
        }
    }
}
