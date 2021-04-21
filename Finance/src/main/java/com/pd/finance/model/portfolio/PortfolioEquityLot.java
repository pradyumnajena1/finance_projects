package com.pd.finance.model.portfolio;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.Date;

public class PortfolioEquityLot {

    @Field(targetType = FieldType.INT64)
    private Long    portfolioId;

    private String portfolioEquityId;

    private int numUnits;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    private Date unitPurchasedDate;

    private Date createdDate;
    private Date updatedDate;

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
}
