package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.InsiderTransaction;
import com.pd.finance.model.InsiderTransactionDetails;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
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
import java.util.Locale;
import java.util.stream.Collectors;
@Component
public class EquityInsiderTransactionDetailsFactory implements IEquityInsiderTransactionDetailsFactory{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityInsiderTransactionDetailsFactory.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd,yyyy");


    @Override
    public InsiderTransactionDetails create(Document document) {

        InsiderTransactionDetails insiderTransactionDetails = new InsiderTransactionDetails();
        List<InsiderTransaction> insiderTransactions = extractInsiderTransactions(document);
        insiderTransactionDetails.setPurchases(insiderTransactions.stream().filter(transaction->isPurchase(transaction)).collect(Collectors.toList()));
        insiderTransactionDetails.setSales(insiderTransactions.stream().filter(transaction->isSale(transaction)).collect(Collectors.toList()));

        insiderTransactionDetails.setUpdatedDate(new Date());
        insiderTransactionDetails.setSource(Constants.SOURCE_MONEY_CONTROL);
        return insiderTransactionDetails;
    }

    private List<InsiderTransaction> extractInsiderTransactions(Document document) {
        List<InsiderTransaction> transactions = new ArrayList<>();

        document.select("#ins > div.deals > div.clearfix > div.bd_bx").stream().forEach(transElement->collectTransaction(transactions,transElement));
        return transactions;
    }

    private void collectTransaction(List<InsiderTransaction> transactions, Element transElement) {
        InsiderTransaction insiderTransaction = new InsiderTransaction();

        insiderTransaction.setTransactionDate(extractTransactionDate(transElement));
        insiderTransaction.setInsiderName(extractInsiderName(transElement));
        insiderTransaction.setInsiderType(extractInsiderType(transElement));
        insiderTransaction.setTransactionType(extractTransactionType(transElement));
        insiderTransaction.setTradedPercentage(extractTradedPercentage(transElement));
        insiderTransaction.setQuantity(extractQuantity(transElement));
        insiderTransaction.setPostTransactionHoldPercentage(extractPostTransactionHoldPercentage(transElement));
        insiderTransaction.setPrice(extractPrice(transElement));

        transactions.add(insiderTransaction);

    }

    private BigDecimal extractPrice(Element transElement) {
        BigDecimal price = null;
        try {
            Element first = transElement.select("table > tbody > tr > td:nth-child(2) > strong").first();
            if(first!=null){

                String priceText = first.text();
                price = CommonUtils.extractDecimalFromText(priceText);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return price;
    }

    private BigDecimal extractPostTransactionHoldPercentage(Element transElement) {
        BigDecimal postTransactionHoldPercentage = null;
        try {
            Element first = transElement.select("table > tbody > tr > td:nth-child(4) > strong").first();
            if(first!=null){

                String postTransactionHoldPercentageText = first.text();
                postTransactionHoldPercentage = CommonUtils.extractDecimalFromText(postTransactionHoldPercentageText);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return postTransactionHoldPercentage;
    }

    private BigInteger extractQuantity(Element transElement) {
        BigInteger quantity = null;
        try {
            Element first = transElement.select("table > tbody > tr > td:nth-child(1) > strong").first();
            if(first!=null){

                String quantityText = first.text();
                quantity = CommonUtils.extractIntegerFromText(quantityText);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return quantity;
    }

    private BigDecimal extractTradedPercentage(Element transElement) {
        BigDecimal tradedPercentage = null;
        try {
            Element first = transElement.select("table > tbody > tr > td:nth-child(3) > strong").first();
            if(first!=null){

                String tradedPercentageText = first.text();
                tradedPercentage = CommonUtils.extractDecimalFromText(tradedPercentageText);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return tradedPercentage;
    }

    private String extractTransactionType(Element transElement) {
        String transactionTypeText = null;
        try {
            Element first = transElement.select("div.clearfix > button").first();
            if(first!=null){

                transactionTypeText = first.text();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return transactionTypeText;
    }

    private String extractInsiderType(Element transElement) {
        String transactionTypeText = null;
        try {
            Element first = transElement.select("div.clearfix > button").first();
            if(first!=null){

                transactionTypeText = first.text();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return transactionTypeText;
    }

    private String extractInsiderName(Element transElement) {
        String insiderNameText = null;
        try {
            Element first = transElement.select("div.brstk_name > h3").first();
            if(first!=null){

                insiderNameText = first.text();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return insiderNameText;
    }

    private Date extractTransactionDate(Element transElement) {
        Date date = null;
        try {
            Element first = transElement.select("div.clearfix > div.br_date").first();
            if(first!=null){

                String dateText = first.text();
                date = CommonUtils.extractDateFromText(dateText,dateFormat);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return date;
    }

    private boolean isSale(InsiderTransaction transaction) {
        return transaction.getTransactionType().toUpperCase( ).startsWith("DISPOSAL");
    }

    private boolean isPurchase(InsiderTransaction transaction) {
        return transaction.getTransactionType().toUpperCase( ).startsWith("ACQUISITION");
    }
}
