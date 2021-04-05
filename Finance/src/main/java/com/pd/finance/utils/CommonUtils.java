package com.pd.finance.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
    public static final int FetchAllEquities = -1;

    public static BigDecimal extractDecimalFromText(String textValue) {
        try {
            if(textValue==null || textValue.equalsIgnoreCase("null")){
                return BigDecimal.ZERO;
            }
            textValue = textValue.replace(",","");
            textValue = textValue.replace("%","");
            textValue = textValue.trim();
            return new BigDecimal( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractDecimalFromText exec failed",ex);
            return BigDecimal.ZERO;
        }
    }
    public static BigInteger extractIntegerFromText(String textValue) {
        try {

            if(textValue==null || textValue.equalsIgnoreCase("null")){
                return BigInteger.ZERO;
            }
            int indexOfComma = textValue.indexOf(',');
            if(indexOfComma != -1){
                textValue = textValue.replace(",","");
            }
            textValue = textValue.trim();
            return new BigInteger( textValue);
        } catch (NumberFormatException ex) {
            logger.error("extractIntegerFromText exec failed",ex);
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

    public static Date extractDateFromText(String textValue,SimpleDateFormat dateFormat) {
        try {

            textValue = textValue.trim();
            return dateFormat.parse( textValue);
        } catch (ParseException ex) {
            logger.error("extractDateFromPerformanceCell exec failed",ex);
            return null;
        }
    }


}
