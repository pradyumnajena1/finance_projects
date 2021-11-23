package com.pd.finance.filter.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.filter.db.SwotFilter;
import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityOverview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.query.Criteria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverviewFilter extends AbstractDBFilter<Equity>  implements EquityFilter {

    private static final Logger logger = LoggerFactory.getLogger(SwotFilter.class);

    @JsonProperty("maxPE")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal maxPE  ;

    @JsonProperty("maxCurrentPrice")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal maxCurrentPrice ;

    @JsonProperty("minMarketCap")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal minMarketCap  ;

    @JsonProperty("minVolume")
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal minVolume ;

    @JsonProperty("sector")
    private String sector;


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public BigDecimal getMaxPE() {
        return maxPE;
    }

    public void setMaxPE(BigDecimal maxPE) {
        this.maxPE = maxPE;
    }

    public BigDecimal getMinMarketCap() {
        return minMarketCap;
    }

    public void setMinMarketCap(BigDecimal minMarketCap) {
        this.minMarketCap = minMarketCap;
    }

    public BigDecimal getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(BigDecimal minVolume) {
        this.minVolume = minVolume;
    }

    public BigDecimal getMaxCurrentPrice() {
        return maxCurrentPrice;
    }

    public void setMaxCurrentPrice(BigDecimal currentPrice) {
        this.maxCurrentPrice = currentPrice;
    }

    @Override
    @JsonIgnore
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (getMaxPE()!=null) {
            criteriaList.add(Criteria.where("overview.stockPE").lte(getMaxPE()) );
            criteriaList.add(Criteria.where("overview.stockPE").gt(BigDecimal.valueOf(0)) );

        }
        if (getMinVolume()!=null) {
            criteriaList.add(Criteria.where("overview.volume").gte(getMinVolume()));
        }
        if (getMinMarketCap()!=null) {
            criteriaList.add(Criteria.where("overview.marketCap").gte(getMinMarketCap()));
        }
        if (getSector()!=null) {
            criteriaList.add(Criteria.where("overview.sector").is(getSector()));
        }

        if (getMaxCurrentPrice()!=null) {
            criteriaList.add(Criteria.where("equityCurrentPriceStats.lastPrice").lte(getMaxCurrentPrice()) );
            criteriaList.add(Criteria.where("equityCurrentPriceStats.lastPrice") .gt(BigDecimal.valueOf(0)));
        }

        return getAsCompositeCriteria(criteriaList);
    }

    @Override
    public boolean apply(Equity equity) {
        boolean isValid = false;

        try {
            EquityOverview equityOverview = equity.getOverview();
            isValid =   getMaxPE().compareTo(equityOverview.getStockPE())>=0 &&
                        getMinVolume().compareTo(equityOverview.getVolume())<=0 &&
                        getMinMarketCap().compareTo(equityOverview.getMarketCap())<=0;

            ;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return isValid;
    }
}
