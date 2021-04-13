package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.BrokerResearchDetails;
import com.pd.finance.model.BrokerResearchLineItem;
import com.pd.finance.model.EquitySwotDetails;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Component
public class EquityBrokerResearchFactory implements IEquityBrokerResearchFactory{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityBrokerResearchFactory.class);
    public static final int NUM_DAYS_To_CACHE = 5;

    @Autowired
    private IDocumentService documentService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");

    public static void main(String[] args) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
            System.out.println( dateFormat.parse("24 Mar, 2021"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BrokerResearchDetails create(Document document) {
        BrokerResearchDetails brokerResearchDetails = new BrokerResearchDetails();
        brokerResearchDetails.setBrokerResearchLineItems(extractBrokerResearchLineItems(document));
        brokerResearchDetails.setSource(Constants.SOURCE_MONEY_CONTROL);
        brokerResearchDetails.setUpdatedDate(new Date());

        return brokerResearchDetails;
    }

    private Set<BrokerResearchLineItem> extractBrokerResearchLineItems(Document document) {
        Set<BrokerResearchLineItem> lineItemSet = new TreeSet<>();
        try {
            Element container = document.select("#broker_research > div.clearfix > div.brrs_stock").first();
            Elements rows = container.select("div.clearfix > div.brrs_bx");
            rows.forEach(row->{
                BrokerResearchLineItem lineItem =   extractBrokerResearchLineItem(row);
                lineItemSet.add(lineItem);
            });
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }

        return lineItemSet;
    }

    private BrokerResearchLineItem extractBrokerResearchLineItem(Element row) {
        BrokerResearchLineItem lineItem = new BrokerResearchLineItem();
        try {
            lineItem.setDate(extractDate(row));
            lineItem.setBrokerName(extractBrokerName(row));
            lineItem.setRating(extractRating(row));
            lineItem.setRecoPrice(extractRecoPrice(row));
            lineItem.setTargetPrice(extractTargetPrice(row));
        } catch (Exception exception) {
            logger.error(exception.getMessage(),exception);
        }


        return lineItem;
    }

    private BigDecimal extractTargetPrice(@NotNull Element row) {
        Element first = row.select("table > tbody > tr > td:nth-child(2) > strong").first();
        BigDecimal targetPrice = null;
        if(first!=null){

            String targetPriceText = first.text();
              targetPrice = CommonUtils.extractDecimalFromText(targetPriceText);
        }
        return targetPrice;
    }

    private BigDecimal extractRecoPrice(@NotNull Element row) {

        Element first = row.select(" table > tbody > tr > td:nth-child(1) > strong").first();
        String recoPriceString = null;
        BigDecimal recoPrice = null;
        if (first!=null) {
            recoPriceString = first.text();
              recoPrice = CommonUtils.extractDecimalFromText(recoPriceString);
        }
        return recoPrice;
    }

    private String extractRating(@NotNull Element row) {
        Element button = row.select("button").first();
        String ratingText = null;
        if (button!=null) {
            ratingText = button.text();
        }
        return ratingText;
    }

    private String extractBrokerName(@NotNull Element row) {
        String brokerName = null;
        Element first = row.select("div.brstk_name > h3").first();
        if (first!=null) {
              brokerName = first.text();
        }
        return brokerName;
    }

    private Date extractDate(@NotNull Element row) {
        Date date = null;
        Element first = row.select("div.br_date").first();
        if(first!=null){

            String dateString = first.text();
            date = CommonUtils.extractDateFromText(dateString, dateFormat);
        }
        return date;
    }
}
