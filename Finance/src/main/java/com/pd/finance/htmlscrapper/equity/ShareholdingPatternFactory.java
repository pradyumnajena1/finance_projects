package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.*;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jetbrains.annotations.NotNull;
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
public class ShareholdingPatternFactory implements IShareholdingPatternFactory {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(ShareholdingPatternFactory.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");

    @Override
    public ShareholdingDetails create(Document document) {
        ShareholdingDetails shareholdingDetails = new ShareholdingDetails();
        List<Date> quarterDates = extractQuarterDates(document);

        document.select("#shareholding > div > table > tbody > tr")
                .stream()
                .forEach(aRow->handleShareholdingPatterRow(shareholdingDetails, quarterDates, aRow));

        shareholdingDetails.setSource(Constants.SOURCE_SCREENER_IO);
        shareholdingDetails.setUpdatedDate(new Date());
        return shareholdingDetails;
    }

    private void handleShareholdingPatterRow(ShareholdingDetails shareholdingDetails, List<Date> quarterDates, Element aRow) {
        String rowHeader = aRow.select("td > button").first().text().toUpperCase();

        if(rowHeader.startsWith("PUBLIC")){
            shareholdingDetails.setPublicShareholding(extractPublicShareholding(aRow, quarterDates));
        }else if(rowHeader.startsWith("FIIS")){
            shareholdingDetails.setFiisShareholding(extractFiisShareholding(aRow, quarterDates));
        }else if(rowHeader.startsWith("DIIS")){
            shareholdingDetails.setDiisShareholding(extractDiisShareholding(aRow, quarterDates));
        }else if(rowHeader.startsWith("GOVERNMENT")){
            shareholdingDetails.setGovernmentShareholding(extractGovernmentShareholding(aRow, quarterDates));
        }else if(rowHeader.startsWith("PROMOTER")){
            shareholdingDetails.setPromoterShareholding(extractPromoterShareholding(aRow, quarterDates));
        }
    }

    private PromoterShareholding extractPromoterShareholding(Element aRow, List<Date> quarterDates) {

        return new PromoterShareholding(getShareholdingPattern(aRow,quarterDates));
    }

    @NotNull
    private List<ShareholdingLineItem> getShareholdingPattern(Element aRow, List<Date> quarterDates) {
        List<ShareholdingLineItem> shareholdingLineItems = new ArrayList<>();
        List<Element> tds = aRow.select("td").stream().collect(Collectors.toList());
        for(int i=1;i<tds.size();i++){
            ShareholdingLineItem lineItem = new ShareholdingLineItem();
            lineItem.setPercentage(CommonUtils.extractDecimalFromText(tds.get(i).text()));
            lineItem.setQuarterDate(quarterDates.get(i));
            shareholdingLineItems.add(lineItem);
        }
        return shareholdingLineItems;
    }

    private GovernmentShareholding extractGovernmentShareholding(Element aRow, List<Date> quarterDates) {
        return new GovernmentShareholding(getShareholdingPattern(aRow,quarterDates));
    }

    private DIISShareholding extractDiisShareholding(Element aRow, List<Date> quarterDates) {
        return new DIISShareholding(getShareholdingPattern(aRow,quarterDates));
    }

    private FIISShareholding extractFiisShareholding(Element aRow, List<Date> quarterDates) {
        return new FIISShareholding(getShareholdingPattern(aRow,quarterDates));
    }

    private PublicShareholding extractPublicShareholding(Element aRow, List<Date> quarterDates) {
        return new PublicShareholding(getShareholdingPattern(aRow,quarterDates));
    }


    private List<Date> extractQuarterDates(Document document){
        List<Date> dates = new ArrayList<>();
        document.select("#shareholding > div > table > thead > tr > th")
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
