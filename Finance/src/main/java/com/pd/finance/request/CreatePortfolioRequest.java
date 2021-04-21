package com.pd.finance.request;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class CreatePortfolioRequest extends AbstractWebRequest {


    private String portfolioName;


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

    }

}
