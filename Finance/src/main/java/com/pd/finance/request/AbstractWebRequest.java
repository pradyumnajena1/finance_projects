package com.pd.finance.request;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWebRequest {
    protected boolean isValidated;
    protected List<String> validationErrors = new ArrayList<>();

    public boolean isValid() {
        doValidate();
        return validationErrors.size() == 0;
    }

    protected void doValidate() {
        if (isValidated = false) {
            validate();
            isValidated = true;
        }
    }

    public abstract void validate();

    public List<String> getValidationErrors() {
        doValidate();
        return validationErrors;
    }


}
