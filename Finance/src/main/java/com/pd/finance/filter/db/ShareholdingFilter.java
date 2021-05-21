package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.GovernmentShareholding;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShareholdingFilter  extends AbstractDBFilter<Equity> implements EquityFilter {
    @JsonProperty("governmentShareholdingFilter")
    private GovernmentShareholdingFilter governmentShareholdingFilter;

    @JsonProperty("publicShareholdingFilter")
    private PublicShareholdingFilter publicShareholdingFilter;

    @JsonProperty("fiisShareholdingFilter")
    private FIISShareholdingFilter fiisShareholdingFilter;

    @JsonProperty("diisShareholdingFilter")
    private DIISShareholdingFilter diisShareholdingFilter;

    @JsonProperty("promoterShareholdingFilter")
    private PromoterShareholdingFilter promoterShareholdingFilter;

    public GovernmentShareholdingFilter getGovernmentShareholdingFilter() {
        return governmentShareholdingFilter;
    }

    public void setGovernmentShareholdingFilter(GovernmentShareholdingFilter governmentShareholdingFilter) {
        this.governmentShareholdingFilter = governmentShareholdingFilter;
    }

    public PublicShareholdingFilter getPublicShareholdingFilter() {
        return publicShareholdingFilter;
    }

    public void setPublicShareholdingFilter(PublicShareholdingFilter publicShareholdingFilter) {
        this.publicShareholdingFilter = publicShareholdingFilter;
    }

    public FIISShareholdingFilter getFiisShareholdingFilter() {
        return fiisShareholdingFilter;
    }

    public void setFiisShareholdingFilter(FIISShareholdingFilter fiisShareholdingFilter) {
        this.fiisShareholdingFilter = fiisShareholdingFilter;
    }

    public DIISShareholdingFilter getDiisShareholdingFilter() {
        return diisShareholdingFilter;
    }

    public void setDiisShareholdingFilter(DIISShareholdingFilter diisShareholdingFilter) {
        this.diisShareholdingFilter = diisShareholdingFilter;
    }

    public PromoterShareholdingFilter getPromoterShareholdingFilter() {
        return promoterShareholdingFilter;
    }

    public void setPromoterShareholdingFilter(PromoterShareholdingFilter promoterShareholdingFilter) {
        this.promoterShareholdingFilter = promoterShareholdingFilter;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        if(getDiisShareholdingFilter()!=null){
            criteriaList.add(getDiisShareholdingFilter().getCriteria("shareholdingDetails"));
        }
        if(getFiisShareholdingFilter()!=null){
            criteriaList.add(getFiisShareholdingFilter().getCriteria("shareholdingDetails"));
        }
        if(getGovernmentShareholdingFilter()!=null){
            criteriaList.add(getGovernmentShareholdingFilter().getCriteria("shareholdingDetails"));
        }
        if(getPromoterShareholdingFilter()!=null){
            criteriaList.add(getPromoterShareholdingFilter().getCriteria("shareholdingDetails"));
        }
        if(getPublicShareholdingFilter()!=null){
            criteriaList.add(getPublicShareholdingFilter().getCriteria("shareholdingDetails"));
        }



        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity obj) {
        return false;
    }
}
