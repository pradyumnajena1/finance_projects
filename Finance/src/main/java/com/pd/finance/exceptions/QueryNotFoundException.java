package com.pd.finance.exceptions;

public class QueryNotFoundException extends Exception{
    public QueryNotFoundException() {
    }

    public QueryNotFoundException(String message) {
        super(message);
    }

    public QueryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryNotFoundException(Throwable cause) {
        super(cause);
    }

    public QueryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
