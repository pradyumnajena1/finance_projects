package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityEssentials;
import com.pd.finance.model.EquityOverview;
import com.pd.finance.utils.CommonUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EquityOverviewFactory {

    @JsonIgnore
    private static Logger logger = LoggerFactory.getLogger(EquityOverviewFactory.class);

    public static EquityOverview create(Document document){

        EquityOverview equityOverview = new EquityOverview();
       equityOverview.setBookValue(extractBookValue(document));
       equityOverview.setFaceValue(extractFaceValue(document));
       equityOverview.setMarketCap(extractMarketCap(document));
       equityOverview.setStockPB(extractPB(document));
       equityOverview.setStockPE(extractPE(document));
       equityOverview.setVolume(extractVolume(document));

        return equityOverview;
    }

    private static BigInteger extractVolume(Document document) {
        try {
            String volumeText = document.select("div#nse_vol").first().childNode(0).attr("text");
            return CommonUtils.extractIntegerFromText(volumeText);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return BigInteger.ZERO;
        }
    }

    private static BigDecimal extractPE(Document document) {
        Element overviewDiv = document.select("div#stk_overview").first();
        Element tbody = overviewDiv.select("tbody").first();
        Element td = tbody.select("tr").first().select("td:eq(2)").first();
        String text = td.childNode(0).attr("text");
        return CommonUtils.extractDecimalFromText(text);
    }

    private static BigDecimal extractPB(Document document) {
        return null;
    }

    private static BigInteger extractMarketCap(Document document) {
        return null;
    }

    private static BigInteger extractFaceValue(Document document) {
        return null;
    }

    private static BigInteger extractBookValue(Document document) {
        return null;
    }


}
