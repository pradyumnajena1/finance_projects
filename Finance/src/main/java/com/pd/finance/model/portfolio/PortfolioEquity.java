package com.pd.finance.model.portfolio;

import java.util.Date;
import java.util.List;

public class PortfolioEquity {

    private String portfolioId;
    private String equityId;
    private List<PortfolioEquityLot> lots;
    private Date createdDate;
    private Date updatedDate;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
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
}
