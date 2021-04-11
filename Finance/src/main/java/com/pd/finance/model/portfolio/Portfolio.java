package com.pd.finance.model.portfolio;

import com.mysema.query.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@QueryEntity
@Document
public class Portfolio {
    @Id
    private String id;
    private String name;
    private String userId;
    private Set<PortfolioEquity> portfolioEquities;
    private Date createdDate;
    private Date updatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PortfolioEquity> getPortfolioEquities() {
        return portfolioEquities;
    }

    public void setPortfolioEquities(Set<PortfolioEquity> portfolioEquities) {
        this.portfolioEquities = portfolioEquities;
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
