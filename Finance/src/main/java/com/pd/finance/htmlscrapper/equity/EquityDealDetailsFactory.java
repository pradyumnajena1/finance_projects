package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.BlockDealDetails;
import com.pd.finance.model.BulkDealDetails;
import com.pd.finance.model.EquityDeal;
import com.pd.finance.model.EquityDealsDetails;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class EquityDealDetailsFactory implements IEquityDealsDetailsFactory{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityDealDetailsFactory.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
    @Override
    public EquityDealsDetails create(Document document) {

        EquityDealsDetails dealsDetails = new EquityDealsDetails();
        dealsDetails.setBlockDealDetails(extractBlockDetails(document));
        dealsDetails.setBulkDealDetails(extractBulkDetails(document));
        dealsDetails.setSource(Constants.SOURCE_MONEY_CONTROL);
        dealsDetails.setUpdatedDate(new Date());
        return dealsDetails;
    }

    private BulkDealDetails extractBulkDetails(Document document) {
        BulkDealDetails bulkDealDetails = new BulkDealDetails();
        List<EquityDeal> equityDeals = extractBulkDeals(document);
        bulkDealDetails.setSaleDeals(equityDeals.stream().filter(aDeal-> isSale(aDeal)).collect(Collectors.toList()));
        bulkDealDetails.setPurchaseDeals(equityDeals.stream().filter(aDeal-> isPurchase(aDeal)).collect(Collectors.toList()));

        return bulkDealDetails;
    }

    private List<EquityDeal> extractBulkDeals(Document document) {
        List<EquityDeal> deals = new ArrayList<>();
        document.select("#buldl > div.dealbx > div.clearfix > div.bd_bx")
                .stream()
                .forEach(dealElement->collectDeal(deals,dealElement));
        return deals;
    }

    private BlockDealDetails extractBlockDetails(Document document) {
        BlockDealDetails blockDealDetails = new BlockDealDetails();
        List<EquityDeal> equityDeals = extractBlockDeals(document);
        blockDealDetails.setSaleDeals(equityDeals.stream().filter(aDeal-> isSale(aDeal)).collect(Collectors.toList()));
        blockDealDetails.setPurchaseDeals(equityDeals.stream().filter(aDeal-> isPurchase(aDeal)).collect(Collectors.toList()));

        return blockDealDetails;
    }

    private List<EquityDeal> extractBlockDeals(Document document) {
        List<EquityDeal> deals = new ArrayList<>();
        document.select("#lockdl > div.dealbx > div.clearfix > div.bd_bx")
                .stream()
                .forEach(dealElement->collectDeal(deals,dealElement));
        return deals;
    }

    private void collectDeal(List<EquityDeal> deals, Element dealElement) {
        EquityDeal equityDeal = createEquityDeal(dealElement);
        deals.add(equityDeal);

    }

    @NotNull
    private EquityDeal createEquityDeal(Element dealElement) {
        EquityDeal equityDeal = new EquityDeal();
        equityDeal.setDealDate(extractDealDate(dealElement));
        equityDeal.setDealType(extractDealType(dealElement));
        equityDeal.setTraderName(extractTraderName(dealElement));
        equityDeal.setTradedPrice(extractTradedPrice(dealElement));
        equityDeal.setTradedUnits(extractTradedUnits(dealElement));
        equityDeal.setTradedPercentage(extractTradedPercentage(dealElement));
        return equityDeal;
    }

    private BigDecimal extractTradedPercentage(Element dealElement) {
        BigDecimal percentage = null;
        try {
            Element first = dealElement.select("table > tbody > tr > td:nth-child(3) > strong").first();
            if(first!=null){

                String percentageText = first.text();
                  percentage = CommonUtils.extractDecimalFromText(percentageText);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return percentage;
    }

    private BigInteger extractTradedUnits(Element dealElement) {
        BigInteger units = null;
        try {
            Element first = dealElement.select("table > tbody > tr > td:nth-child(1) > strong").first();
            if(first!=null){

                String unitsText = first.text();
                units = CommonUtils.extractIntegerFromText(unitsText);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return units;
    }

    private BigDecimal extractTradedPrice(Element dealElement) {
        BigDecimal tradedPrice = null;
        try {
            Element first = dealElement.select("table > tbody > tr > td:nth-child(2) > strong").first();
            if(first!=null){

                String priceText = first.text();
                tradedPrice = CommonUtils.extractDecimalFromText(priceText);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return tradedPrice;
    }

    private String extractTraderName(Element dealElement) {

        String traderName = null;
        try {
            Element first = dealElement.select("div.brstk_name > h3").first();
            if(first!=null){

                traderName = first.text();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return traderName;
    }

    private String extractDealType(Element dealElement) {
        String dealTypeText = null;
        try {
            Element first = dealElement.select("div.clearfix > button").first();
            if(first!=null){

                  dealTypeText = first.text();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return dealTypeText;
    }

    private Date extractDealDate(Element dealElement) {
        Date date = null;
        try {
            Element first = dealElement.select("div.clearfix > div.br_date").first();
            if(first!=null){

                String dateText = first.text();
                  date = CommonUtils.extractDateFromText(dateText,dateFormat);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return date;
    }

    private boolean isSale(EquityDeal aDeal) {
        return "Sale".equalsIgnoreCase( aDeal.getDealType() );
    }
    private boolean isPurchase(EquityDeal aDeal) {
        return "Purchase".equalsIgnoreCase( aDeal.getDealType() );
    }
}
