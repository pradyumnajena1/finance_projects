package com.pd.finance.converter;

import com.pd.finance.controller.WebCrawlerController;
import com.pd.finance.model.equity.summary.SummaryProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummaryProfileConverter implements IConverter<com.pd.finance.response.summary.SummaryProfile  , SummaryProfile> {
    private Logger logger = LoggerFactory.getLogger(SummaryProfileConverter.class);

    @Override
    public SummaryProfile convert(com.pd.finance.response.summary.SummaryProfile summaryProfile) {
        SummaryProfile result = null;
        try {
            if(summaryProfile==null){
                return null;
            }
            result = new SummaryProfile();
            result.setAddress1(summaryProfile.getAddress1());
            result.setAddress2(summaryProfile.getAddress2());
            result.setCity(summaryProfile.getCity());
            result.setCompanyOfficers(summaryProfile.getCompanyOfficers());
            result.setCountry(summaryProfile.getCountry());
            result.setFax(summaryProfile.getFax());
            result.setFullTimeEmployees(summaryProfile.getFullTimeEmployees());
            result.setIndustry(summaryProfile.getIndustry());
            result.setLongBusinessSummary(summaryProfile.getLongBusinessSummary());
            result.setSector(summaryProfile.getSector());
            result.setWebsite(summaryProfile.getWebsite());
            result.setPhone(summaryProfile.getPhone());
            result.setZip(summaryProfile.getZip());
            result.setMaxAge(summaryProfile.getMaxAge());
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }
        return result;
    }
}
