package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.EquityOverview;
import com.pd.finance.utils.CommonUtils;
import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Component
public class EquityOverviewFactory implements IEquityOverviewFactory {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityOverviewFactory.class);

    @Override
    public   EquityOverview create(Document document){

        EquityOverview equityOverview = new EquityOverview();
       equityOverview.setBookValue(extractBookValue(document));
       equityOverview.setFaceValue(extractFaceValue(document));
       equityOverview.setMarketCap(extractMarketCap(document));
       equityOverview.setStockPB(extractPB(document));
       equityOverview.setStockPE(extractPE(document));
       equityOverview.setVolume(extractVolume(document));

       equityOverview.setSource(Constants.SOURCE_MONEY_CONTROL);
       equityOverview.setUpdatedDate(new Date());

        return equityOverview;
    }

    private   BigInteger extractVolume(Document document) {
        try {
            String volumeText = document.select("div#nse_vol").first().childNode(0).attr("text");
            return CommonUtils.extractIntegerFromText(volumeText);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return BigInteger.ZERO;
        }
    }

    private   BigDecimal extractPE(Document document) {
        Element overviewDiv = document.select("div#stk_overview").first();
        Element tableBody = overviewDiv.select("tbody").first();
        Element td = tableBody.select("tr").first().select("td:eq(1)").first();
        String text = td.childNode(0).attr("text");
        return CommonUtils.extractDecimalFromText(text);
    }

    private   BigDecimal extractPB(Document document) {

        Element overviewDiv = document.select("div#stk_overview").first();
        Element pb = overviewDiv.select("div > div > div:nth-child(4) > table > tbody > tr:nth-child(1) > td.nsepb.bsepb").first();

        String text = pb.text();
        return CommonUtils.extractDecimalFromText(text);
    }

    private   BigInteger extractMarketCap(Document document) {
        Element overviewDiv = document.select("div#stk_overview").first();
        Element marketCap = overviewDiv.select("div > div > div:nth-child(2) > table > tbody > tr:nth-child(2) > td.nsemktcap.bsemktcap").first();

        String text = marketCap.text();
        return CommonUtils.extractIntegerFromText(text);
    }

    private   BigDecimal extractFaceValue(Document document) {

        Element overviewDiv = document.select("div#stk_overview").first();
        Element  faceValue =  overviewDiv.select("div > div > div:nth-child(3) > table > tbody > tr:nth-child(1) > td.nsefv.bsefv").first();

        String text = faceValue.text();
        return CommonUtils.extractDecimalFromText(text);
    }

    private   BigDecimal extractBookValue(Document document) {

        Element overviewDiv = document.select("div#stk_overview").first();
        Element bookValue =  overviewDiv.select(" div > div > div:nth-child(3) > table > tbody > tr:nth-child(5) > td.nsebv.bsebv").first();

        String text = bookValue.text();
        return CommonUtils.extractDecimalFromText(text);
    }


}
