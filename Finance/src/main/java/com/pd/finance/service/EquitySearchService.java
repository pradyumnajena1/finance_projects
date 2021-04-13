package com.pd.finance.service;

import com.pd.finance.filter.*;
import com.pd.finance.filter.code.EquityInsightFilter;
import com.pd.finance.filter.code.EquityNamesFilter;
import com.pd.finance.filter.code.PerformanceFilter;
import com.pd.finance.filter.db.*;
import com.pd.finance.model.*;

import com.pd.finance.persistence.EquityRepository;
import com.pd.finance.request.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class EquitySearchService implements IEquitySearchService {
    private static final Logger logger = LoggerFactory.getLogger(EquitySearchService.class);
    @Autowired
    private EquityRepository equityRepository;

    @Override
    public List<Equity> search(EquitySearchRequest searchRequest){
        logger.info("search exec started for searchRequest "+searchRequest);
        List<Equity> equitiesCollector  = new ArrayList<>();

        List<IFilter<Equity>> filters = searchRequest.getFilters();
        List<IFilter<Equity>> dbFilters = getFilters(filters, FilterType.InDb);
        List<IFilter<Equity>> inCodeFilters = getFilters(filters, FilterType.InCode);
        List<IFilter<Equity>> partialFilters = getFilters(filters, FilterType.Partial);


        Criteria dbFilterCriteria = getCriteria(dbFilters, partialFilters);
        final CompositeIncodeFilter<Equity> compositeFilter = getEquityCompositeIncodeFilter(inCodeFilters, partialFilters);


        Pageable  pageRequest = PageRequest.of(0, 500);

        Page<Equity> page = getPage(dbFilterCriteria, pageRequest);
        logger.info("Search No of equities after applying db_filters {} ",page.getTotalElements());

        while (!page.isEmpty())
         {
             int currentPageNumber = page.getNumber();
             logger.info("search process started pageNumber {} ", currentPageNumber);

             List<Equity> equities = page.toList();
             logger.info("search  pageNumber {} numEquities {} ", currentPageNumber, equities.size());

             List<Equity> filteredEquities = filterEquities(compositeFilter, equities);
             equitiesCollector.addAll(filteredEquities);

             logger.info("search pageNumber {} numEquities {} after in_code filters", currentPageNumber, equitiesCollector.size());


             logger.info("search process completed pageNumber {}", currentPageNumber);
             pageRequest = pageRequest.next();
             page = getPage(dbFilterCriteria,  pageRequest);

        }
        logger.info("search exec completed for searchRequest "+searchRequest);
        return equitiesCollector;
    }

    @Override
    public List<Equity> searchByName(EquitySearchByNameRequest request) {

        logger.info("searchByName exec started for searchRequest "+request);
        List<Equity> equitiesCollector  = new ArrayList<>();

        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(request.getSearchTerms().toArray(new String[0]));
        Query query = TextQuery.queryText(criteria);
        Pageable  pageRequest = PageRequest.of(0, 500);
        Page<Equity> page = equityRepository.searchEquity(query,pageRequest);
        int totalPages = page.getTotalPages();
        int currentPageNumber = page.getNumber();
        logger.info("searchByName No of equities after applying db_filters {} ",page.getTotalElements());

        while (!page.isEmpty() && currentPageNumber<totalPages)
        {

            logger.info("searchByName process started pageNumber {} ", currentPageNumber);

            List<Equity> equities = page.toList();
            logger.info("searchByName  pageNumber {} numEquities {} ", currentPageNumber, equities.size());
            equitiesCollector.addAll(equities);
            logger.info("searchByName pageNumber {} numEquities {} after in_code filters", currentPageNumber, equitiesCollector.size());
            logger.info("search process completed pageNumber {}", currentPageNumber);


            pageRequest = pageRequest.next();
            page = equityRepository.searchEquity(query,pageRequest);
            currentPageNumber = page.getNumber();

        }
        logger.info("search exec completed for searchRequest "+request);
        return equitiesCollector;

    }

    @NotNull
    protected List<Equity> filterEquities(CompositeIncodeFilter<Equity> compositeFilter, List<Equity> equities) {
        return equities.parallelStream().filter(equity -> compositeFilter.apply(equity)).collect(Collectors.toList());
    }

    protected Page<Equity> getPage(Criteria dbFilterCriteria, Pageable pageRequest) {


        return dbFilterCriteria != null ? equityRepository.searchEquity(dbFilterCriteria, pageRequest) : equityRepository.findAll(pageRequest);
    }

    @NotNull
    protected List<IFilter<Equity>> getFilters(List<IFilter<Equity>> filters, FilterType inDb) {
        return filters.stream().filter(aFilter -> aFilter.getFilterType().equals(inDb)).collect(Collectors.toList());
    }

    @NotNull
    protected CompositeIncodeFilter<Equity> getEquityCompositeIncodeFilter(List<IFilter<Equity>> inCodeFilters, List<IFilter<Equity>> partialFilters) {
        final List<IFilter<Equity>> cumulatedIncodeFilters = new ArrayList<>();
        cumulatedIncodeFilters.addAll(inCodeFilters);
        cumulatedIncodeFilters.addAll(partialFilters);

        return new CompositeIncodeFilter<Equity>(cumulatedIncodeFilters,"And");
    }


    protected Criteria getCriteria(List<IFilter<Equity>> dbFilters, List<IFilter<Equity>> partialFilters) {
        List<Criteria> criteriaList = new ArrayList<>();

        dbFilters.stream().forEach(dbFilter->{
            Criteria criteria = dbFilter.getCriteria("");
            criteriaList.add(criteria);
        });

        partialFilters.stream().forEach(partialFilter->{
            Criteria criteria = partialFilter.getCriteria("");
            criteriaList.add(criteria);
        });
        Criteria criteria = null;
        if(criteriaList.size()>1){
              criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        }else if(criteriaList.size()==1){
              criteria =  criteriaList.get(0);
        }


        return criteria;
    }




}
