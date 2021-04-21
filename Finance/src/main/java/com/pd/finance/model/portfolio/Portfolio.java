package com.pd.finance.model.portfolio;

import com.mysema.query.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.Set;

@QueryEntity
@Document
public class Portfolio {
    @Transient
    public static final String SEQUENCE_NAME = "users_portfolio_sequence";
    @Id
    private Long id;

    private String name;

    @Field(targetType = FieldType.INT64)
    private Long userId;

    private Set<PortfolioEquity> portfolioEquities;
    private Date createdDate;
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
