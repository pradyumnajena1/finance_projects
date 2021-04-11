package com.pd.finance.exceptions;

import java.util.List;

public class ValidationException extends Exception{
    private List<String> validationErrors;

    public ValidationException(List<String> validationErrors) {
        super(new IllegalArgumentException("Validation Errors"));
        this.validationErrors = validationErrors;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
