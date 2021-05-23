package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.*;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquityFundamentalRatioFactory implements IEquityFundamentalRatioFactory{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityFundamentalRatioFactory.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");


    @Override
    public FundamentalRatios create(Document document) {
        FundamentalRatios fundamentalRatios = new FundamentalRatios();
        List<Date> quarterDates = extractQuarterDates(document);

        document.select("#ratios > div.responsive-holder.fill-card-width > table > tbody > tr")
                .stream()
                .forEach(aRow->handleFundamentalRatioRow(fundamentalRatios, quarterDates, aRow));

        fundamentalRatios.setSource(Constants.SOURCE_SCREENER_IO);
        fundamentalRatios.setUpdatedDate(new Date());
        return fundamentalRatios;
    }

    private void handleFundamentalRatioRow(FundamentalRatios fundamentalRatios, List<Date> quarterDates, Element aRow) {
         String rowHeader = aRow.select("td.text").first().text().toUpperCase();

        if(rowHeader.startsWith("ROCE")){
            fundamentalRatios.setRoce(extractROCE(aRow, quarterDates));
        }else if(rowHeader.startsWith("DEBTOR DAYS")){
            fundamentalRatios.setDebtorDays(extractDebtorDays(aRow, quarterDates));
        }else if(rowHeader.startsWith("INVENTORY TURNOVER")){
            fundamentalRatios.setInventoryTurnOver(extractInventoryTurnover(aRow, quarterDates));
        }
    }

    private InventoryTurnOver extractInventoryTurnover(Element aRow, List<Date> quarterDates) {
        return new InventoryTurnOver(extractFundamentalRatio(aRow,quarterDates));
    }

    private List<FundamentalRatioLineItem> extractFundamentalRatio(Element aRow, List<Date> quarterDates) {
        List<FundamentalRatioLineItem> fundamentalRatioLineItems = new ArrayList<>();
        try {
            List<Element> tds = aRow.select("td").stream().collect(Collectors.toList());
            for(int i=1;i<tds.size();i++){
                FundamentalRatioLineItem lineItem = new FundamentalRatioLineItem();
                lineItem.setPercentage(CommonUtils.extractDecimalFromText(tds.get(i).text()));
                lineItem.setQuarterDate(quarterDates.get(i));

                fundamentalRatioLineItems.add(lineItem);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return fundamentalRatioLineItems;
    }

    private DebtorDays extractDebtorDays(Element aRow, List<Date> quarterDates) {
        return new DebtorDays(extractFundamentalRatio(aRow,quarterDates));
    }

    private Roce extractROCE(Element aRow, List<Date> quarterDates) {
        return new Roce(extractFundamentalRatio(aRow,quarterDates));
    }

    private List<Date> extractQuarterDates(Document document){
        List<Date> dates = new ArrayList<>();

        document.select("#ratios > div.responsive-holder.fill-card-width > table > thead > tr > th")
                .stream()
                .forEach(th->{Date date = extractDate(th);dates.add(date);});
        return dates;
    }
    private Date extractDate(Element thElement) {
        Date date = null;
        try {
            if(thElement!=null){
                String dateText = thElement.text();
                date = CommonUtils.extractDateFromText(dateText,dateFormat);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return date;
    }
}
