package com.pd.finance.model.portfolio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class PortfolioEquityLot implements Comparable<PortfolioEquityLot>{

    @JsonIgnore
    private static Comparator<PortfolioEquityLot> portfolioEquityLotComparator = Comparator
            .comparing(PortfolioEquityLot::getPortfolioId, CommonUtils.nullSafeLongComparator)
            .thenComparing(PortfolioEquityLot::getPortfolioEquityId, CommonUtils.nullSafeStringComparator)
            .thenComparing(PortfolioEquityLot::getPortfolioEquityLotId, CommonUtils.nullSafeStringComparator);

    private String portfolioEquityLotId;
    @Field(targetType = FieldType.INT64)
    private Long    portfolioId;

    private String portfolioEquityId;


    @Field(targetType = FieldType.INT32)
    private int numUnits;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date unitPurchasedDate;

    private Date createdDate;
    private Date updatedDate;

    public PortfolioEquityLot(Long portfolioId, String portfolioEquityId,String portfolioEquityLotId) {
        this.portfolioEquityLotId = portfolioEquityLotId;
        this.portfolioId = portfolioId;
        this.portfolioEquityId = portfolioEquityId;
    }

    public PortfolioEquityLot(Long portfolioId, String portfolioEquityId, int numUnits, BigDecimal price, Date unitPurchasedDate) {
        this.portfolioId = portfolioId;
        this.portfolioEquityId = portfolioEquityId;
        this.numUnits = numUnits;
        this.price = price;
        this.unitPurchasedDate = unitPurchasedDate;
        this.portfolioEquityLotId = UUID.randomUUID().toString();
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public PortfolioEquityLot() {
        this.portfolioEquityLotId = UUID.randomUUID().toString();
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public String getPortfolioEquityLotId() {
        return portfolioEquityLotId;
    }

    public void setPortfolioEquityLotId(String portfolioEquityLotId) {
        this.portfolioEquityLotId = portfolioEquityLotId;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioEquityId() {
        return portfolioEquityId;
    }

    public void setPortfolioEquityId(String portfolioEquityId) {
        this.portfolioEquityId = portfolioEquityId;
    }

    public int getNumUnits() {
        return numUnits;
    }

    public void setNumUnits(int numUnits) {
        this.numUnits = numUnits;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getUnitPurchasedDate() {
        return unitPurchasedDate;
    }

    public void setUnitPurchasedDate(Date unitPurchasedDate) {
        this.unitPurchasedDate = unitPurchasedDate;
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

    @Override
    public int compareTo(@NotNull PortfolioEquityLot other) {
        return portfolioEquityLotComparator.compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioEquityLot equityLot = (PortfolioEquityLot) o;
        return  portfolioId.equals(equityLot.portfolioId) && portfolioEquityId.equals(equityLot.portfolioEquityId) && portfolioEquityLotId.equals(equityLot.portfolioEquityLotId) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(portfolioId, portfolioEquityId,portfolioEquityLotId);
    }
}
