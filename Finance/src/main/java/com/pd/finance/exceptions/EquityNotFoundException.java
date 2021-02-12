package com.pd.finance.exceptions;

public class EquityNotFoundException extends  Exception{
    public EquityNotFoundException() {
    }

    public EquityNotFoundException(String message) {
        super(message);
    }

    public EquityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EquityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EquityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
