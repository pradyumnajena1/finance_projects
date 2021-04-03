package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.*;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
@Component
public class EquityProfitLossDetailsFactory implements IEquityProfitLossDetailsFactory{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityProfitLossDetailsFactory.class);
    @Autowired
    private IDocumentService documentService;

    @Override
    public EquityProfitLossDetails create(Document document) {
        EquityProfitLossDetails profitLossDetails = new EquityProfitLossDetails();
        profitLossDetails.setProfitGrowthDetails(extractProfitGrowthDetails(document));
        profitLossDetails.setReturnOnEquity(extractReturnOnEquity(document));
        profitLossDetails.setSalesGrowthDetails(extractSalesGrowthDetails(document));
        profitLossDetails.setStockPriceCagr(extractStockPriceCagr(document));

        profitLossDetails.setSource(Constants.SOURCE_SCREENER_IO);
        profitLossDetails.setUpdatedDate(new Date());
        return profitLossDetails;
    }
    private CompoundedSalesGrowthDetails extractSalesGrowthDetails(Document document) {
        CompoundedSalesGrowthDetails compoundedSalesGrowthDetails = new CompoundedSalesGrowthDetails();
        try {
            Elements rows = document.select(getCssQuery(1));
            Element[] elements = rows.toArray(new Element[0]);
            if(elements.length>1){
                compoundedSalesGrowthDetails.setLastTenYears(getProfitLossDecimal(rows.get(1)));
            }
            if(elements.length>2){
                compoundedSalesGrowthDetails.setLastFiveYears(getProfitLossDecimal(rows.get(2)));
            }
            if(elements.length>3){
                compoundedSalesGrowthDetails.setLastThreeYears(getProfitLossDecimal(rows.get(3)));
            }
            if(elements.length>4){
                compoundedSalesGrowthDetails.setTtm(getProfitLossDecimal(rows.get(4)));
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }

        return compoundedSalesGrowthDetails;
    }
    private CompoundedProfitGrowthDetails extractProfitGrowthDetails(Document document) {
        CompoundedProfitGrowthDetails profitGrowthDetails = new CompoundedProfitGrowthDetails();
        try {
            Elements rows = document.select(getCssQuery(2));
            Element[] elements = rows.toArray(new Element[0]);

            if(elements.length>1){
                profitGrowthDetails.setLastTenYears(getProfitLossDecimal(rows.get(1)));
            }
            if(elements.length>2){
                profitGrowthDetails.setLastFiveYears(getProfitLossDecimal(rows.get(2)));
            }
            if(elements.length>3){
                profitGrowthDetails.setLastThreeYears(getProfitLossDecimal(rows.get(3)));
            }
            if(elements.length>4){
                profitGrowthDetails.setTtm(getProfitLossDecimal(rows.get(4)));
            }




        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }

        return profitGrowthDetails;
    }

    private CompoundedStockPriceCagr extractStockPriceCagr(Document document) {
        CompoundedStockPriceCagr stockPriceCagr = new CompoundedStockPriceCagr();

        try {
            Elements rows = document.select(getCssQuery(3));
            Element[] elements = rows.toArray(new Element[0]);

            if(elements.length>1){
                stockPriceCagr.setLastTenYears(getProfitLossDecimal(rows.get(1)));
            }
            if(elements.length>2){
                stockPriceCagr.setLastFiveYears(getProfitLossDecimal(rows.get(2)));
            }
            if(elements.length>3){
                stockPriceCagr.setLastThreeYears(getProfitLossDecimal(rows.get(3)));
            }
            if(elements.length>4){
                stockPriceCagr.setLastOneYear(getProfitLossDecimal(rows.get(4)));
            }


        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }

        return stockPriceCagr;
    }
    private CompoundedReturnOnEquity extractReturnOnEquity(Document document) {
        CompoundedReturnOnEquity returnOnEquity = new CompoundedReturnOnEquity();
        try {
            Elements rows = document.select(getCssQuery(4));
            Element[] elements = rows.toArray(new Element[0]);
            if(elements.length>1){
                returnOnEquity.setLastTenYears(getProfitLossDecimal(rows.get(1)));
            }
            if(elements.length>2){
                returnOnEquity.setLastFiveYears(getProfitLossDecimal(rows.get(2)));
            }
            if(elements.length>3){
                returnOnEquity.setLastThreeYears(getProfitLossDecimal(rows.get(3)));
            }
            if(elements.length>4){
                returnOnEquity.setLastOneYear(getProfitLossDecimal(rows.get(4)));
            }

        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }

        return returnOnEquity;
    }


    @NotNull
    private String getCssQuery(int tableNum) {

        return   "#profit-loss > div:nth-child(3) > table:nth-child("+tableNum+ ") > tbody > tr" ;
    }





    private BigDecimal getProfitLossDecimal(Element row) {

        String text = row.select(" td:nth-child(2)").first().text();
        return CommonUtils.extractDecimalFromText(text);
    }

}
