package com.pd.finance.request;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class CreatePortfolioRequest extends AbstractWebRequest {

    private String userId;
    private String portfolioName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    @Override
    public void validate() {
        if(StringUtils.isBlank(portfolioName)){
            validationErrors.add("portfolioName cant be empty");
        }
        if(StringUtils.isBlank(userId)){
            validationErrors.add("userId cant be empty");
        }
    }

}
