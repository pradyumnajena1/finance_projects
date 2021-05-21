package com.pd.finance.model.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.model.BrokerResearchLineItem;
import com.pd.finance.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.*;

public class PortfolioEquity  implements Comparable<PortfolioEquity>{
    @JsonIgnore
    private static Comparator<PortfolioEquity> portfolioEquityComparator = Comparator
            .comparing(PortfolioEquity::getPortfolioId, CommonUtils.nullSafeLongComparator)
            .thenComparing(PortfolioEquity::getEquityId, CommonUtils.nullSafeStringComparator);

    public PortfolioEquity(Long portfolioId, String equityId) {
        this.portfolioId = portfolioId;
        this.equityId = equityId;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public PortfolioEquity() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    @Field(targetType = FieldType.INT64)
    private Long portfolioId;

    private String equityId;

    private List<PortfolioEquityLot> lots = new ArrayList<>();

    private Date createdDate;
    private Date updatedDate;

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getEquityId() {
        return equityId;
    }

    public void setEquityId(String equityId) {
        this.equityId = equityId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<PortfolioEquityLot> getLots() {
        return lots;
    }

    public void setLots(List<PortfolioEquityLot> lots) {
        this.lots = lots;
    }

    @Override
    public int compareTo(@NotNull PortfolioEquity other) {

        return portfolioEquityComparator.compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioEquity that = (PortfolioEquity) o;
        return portfolioId.equals(that.portfolioId) && equityId.equals(that.equityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, equityId);
    }
}
