package com.pd.finance.utils;


import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
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
            logger.error("extractDecimalFromText exec failed  for input "+textValue+" error: "+ex.getMessage());
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
            logger.error("extractIntegerFromText exec failed for input "+textValue+" error: "+ex.getMessage());
            return BigInteger.ZERO;
        }
    }




    public static Date extractDateFromText(String textValue) {
        try {

            textValue = textValue.trim();
            return dateFormat.parse( textValue);
        } catch (ParseException ex) {
            logger.error("extractDateFromPerformanceCell exec failed error: "+ex.getMessage());
            return null;
        }
    }

    public static Date extractDateFromText(String textValue,SimpleDateFormat dateFormat) {
        try {

            textValue = textValue.trim();
            return dateFormat.parse( textValue);
        } catch (ParseException ex) {
            logger.error("extractDateFromPerformanceCell exec failed error: "+ex.getMessage());
            return null;
        }
    }


    public static Instant atStartOfDay(Instant instant) {
        return Instant.ofEpochMilli(atStartOfDay(new Date(instant.toEpochMilli())).getTime());
    }
    public static Instant atEndOfDay(Instant instant) {
        return Instant.ofEpochMilli(atEndOfDay(new Date(instant.toEpochMilli())).getTime());
    }
    public static Date atEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date atStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static <T extends Comparable<T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static boolean isWithinRange(BigDecimal value, BigDecimal target, BigDecimal percentage) {

        BigDecimal end  = getUpperBound(target, percentage);
        BigDecimal start  = getLowerBound(target, percentage);
        return CommonUtils.isBetween(value,start,end);

    }

    public static BigDecimal getLowerBound(BigDecimal target, BigDecimal percentage) {
        return target.multiply(BigDecimal.ONE.subtract(percentage));
    }

    public static BigDecimal getUpperBound(BigDecimal target, BigDecimal percentage) {
        return target.multiply(percentage.add(BigDecimal.ONE));
    }

    public static String  getHexString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<byteArray.length;i++){
            String substring = Integer.toString((byteArray[i] & 0xff) + 0x100, 16).substring(1);
            sb.append(substring);
        }
        return sb.toString();
    }
    public static   byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
    public static Comparator<String> nullSafeStringComparator = Comparator
            .nullsFirst(String::compareToIgnoreCase);

    public static Comparator<Date> nullSafeDateComparator = Comparator
            .nullsFirst(Date::compareTo);
    public static Comparator<Long> nullSafeLongComparator = Comparator
            .nullsFirst(Long::compareTo);

}
