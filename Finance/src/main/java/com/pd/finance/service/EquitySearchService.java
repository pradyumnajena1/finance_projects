package com.pd.finance.service;

import com.pd.finance.filter.*;
import com.pd.finance.filter.code.EquityInsightFilter;
import com.pd.finance.filter.code.PerformanceFilter;
import com.pd.finance.filter.db.OverviewFilter;
import com.pd.finance.filter.db.SwotFilter;
import com.pd.finance.filter.db.TechnicalPeriodFilter;
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
import org.springframework.stereotype.Service;

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
        List<Equity> equitiesCollector  = new ArrayList<>();

        List<IFilter<Equity>> filters = getFilters(searchRequest);
        List<IFilter<Equity>> dbFilters = getFilters(filters, FilterType.InDb);
        List<IFilter<Equity>> inCodeFilters = getFilters(filters, FilterType.InCode);
        List<IFilter<Equity>> partialFilters = getFilters(filters, FilterType.Partial);


        Criteria dbFilterCriteria = getCriteria(dbFilters, partialFilters);
        final CompositeIncodeFilter<Equity> compositeIncodeFilter = getEquityCompositeIncodeFilter(inCodeFilters, partialFilters);


        Pageable  pageRequest = PageRequest.of(0, 500);

        Page<Equity> page = getPage(dbFilterCriteria, pageRequest);

        while (!page.isEmpty())
         {
             List<Equity> list = page.filter(equity -> compositeIncodeFilter.apply(equity)).toList();

             equitiesCollector.addAll(list);
             pageRequest = pageRequest.next();

             page = getPage(dbFilterCriteria,  pageRequest);

        }

        return equitiesCollector;
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

    private List<IFilter<Equity>> getFilters(EquitySearchRequest searchRequest) {
        List<IFilter<Equity>> filters = new ArrayList<>();
        OverviewFilter overviewFilter = searchRequest.getOverviewFilter();
        if(overviewFilter!=null){
            filters.add(overviewFilter);
        }
        PerformanceFilter performanceFilter = searchRequest.getPerformanceFilter();
        if(performanceFilter!=null){
            filters.add(performanceFilter);
        }

        SwotFilter swotFilter = searchRequest.getSwotFilter();
        if(swotFilter!=null){
            filters.add(swotFilter);
        }

        TechnicalPeriodFilter technicalPeriodFilter = searchRequest.getTechnicalPeriodFilter();
        if(technicalPeriodFilter!=null){
            filters.add(technicalPeriodFilter);
        }
        EquityInsightFilter insightFilter = searchRequest.getInsightFilter();
        if(insightFilter!=null){
            filters.add(insightFilter);
        }
        return filters;
    }


}
