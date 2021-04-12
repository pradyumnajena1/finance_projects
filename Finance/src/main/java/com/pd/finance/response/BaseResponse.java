package com.pd.finance.response;

public class BaseResponse {


    private boolean success;
    private Object payload;
    private String  errorMessage;

    public BaseResponse(Object payload) {
        this.payload = payload;
        this.success = true;
    }

    public BaseResponse(Throwable exception) {
        this.errorMessage = exception.getMessage();
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
