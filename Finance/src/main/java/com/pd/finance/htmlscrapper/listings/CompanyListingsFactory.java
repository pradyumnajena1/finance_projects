package com.pd.finance.htmlscrapper.listings;

import com.pd.finance.controller.WebCrawlerController;
import com.pd.finance.service.IDocumentService;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CompanyListingsFactory implements ICompanyListingsFactory {
    private Logger logger = LoggerFactory.getLogger(CompanyListingsFactory.class);
    @Autowired
    private IDocumentService documentService;

    @Override
    public List<String> getCompanyNames(Document document,int numCompanies){
        List<String> namesCollector  = new ArrayList<>();
        String nextPage = document.location();
       while(document !=null) {
           document.select("#leftcontainer > table:nth-child(4) > tbody > tr > td:nth-child(1) > table > tbody > tr").stream().forEach(element -> extractNames(element, namesCollector));
           document.select("#leftcontainer > table:nth-child(4) > tbody > tr > td:nth-child(2) > table > tbody > tr").stream().forEach(element -> extractNames(element, namesCollector));

         nextPage = getNextPageLink(document);

         document = getNextPageDocument(nextPage);

       }

          return namesCollector;
    }

    private Document getNextPageDocument(String nextPage)   {
        Document document = null;
        try {
            if(StringUtils.isBlank(nextPage)){
                return document;
            }
            document =  documentService.getDocument(nextPage,null);
        } catch (Exception e) {
           logger.error(e.getMessage(),e);
        }
        return document;
    }

    private void extractNames(Element row, List<String> namesCollector) {
        String companyName = row.select(" td:nth-child(1)").first().text();
        namesCollector.add(companyName);
    }

    private String getNextPageLink(Document document){
        String nextPage = null;
        Element first = document.select("#leftcontainer > table.pagination-container-company > tbody > tr > td > a:nth-child(1)").first();

        if(first != null && "Next".equalsIgnoreCase(first.text().trim())){
            nextPage= first.attr("href");
        }
        Element second = document.select("#leftcontainer > table.pagination-container-company > tbody > tr > td > a:nth-child(2)").first();
        if(second!=null && "Next".equalsIgnoreCase(second.text().trim())){
            nextPage =  second.attr("href");
        }
        if(StringUtils.isNotBlank(nextPage)){
           nextPage =  nextPage.startsWith("https:")?nextPage:"https:"+nextPage;
        }
        return nextPage ;
    }
}
