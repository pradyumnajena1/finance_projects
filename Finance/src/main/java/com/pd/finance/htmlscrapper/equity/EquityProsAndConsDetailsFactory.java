package com.pd.finance.htmlscrapper.equity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.EquityProfitLossDetails;
import com.pd.finance.model.EquityProsAndConsDetails;
import com.pd.finance.service.IDocumentService;
import com.pd.finance.utils.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class EquityProsAndConsDetailsFactory implements IEquityProsAndConsDetailsFactory{
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EquityProsAndConsDetailsFactory.class);
    @Autowired
    private IDocumentService documentService;


    @Override
    public EquityProsAndConsDetails create(Document document) {
        EquityProsAndConsDetails prosAndConsDetails = new EquityProsAndConsDetails();
        prosAndConsDetails.setPros(extractPros(document));
        prosAndConsDetails.setCons(extractCons(document));

        prosAndConsDetails.setSource(Constants.SOURCE_SCREENER_IO);
        prosAndConsDetails.setUpdatedDate(new Date());
        return prosAndConsDetails;
    }

    private List<String> extractCons(Document document) {
        List<String> cons = new ArrayList<>();

        try {
            Element ul = document.select("#analysis > div > div.cons > ul").first();
            if(ul!=null){
                Elements lis = ul.select("li");
                if(lis!=null){

                    lis.stream().forEach(li-> cons.add(  li.text()));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return cons;
    }

    private List<String> extractPros(Document document) {
        List<String> pros = new ArrayList<>();
        try {
            Element ul = document.select("#analysis > div > div.pros > ul").first();
            if(ul!=null){
                Elements lis = ul.select("li");
                if(lis!=null){

                    lis.stream().forEach(li-> pros.add(  li.text()));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return pros;
    }
}
