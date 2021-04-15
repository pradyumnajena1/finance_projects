package com.pd.finance.model;

import com.mysema.query.annotations.QueryEntity;
import com.pd.finance.request.EquitySearchRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@QueryEntity
@Document
public class UserEquityQuery {

    @Transient
    public static final String SEQUENCE_NAME = "users_query_sequence";

    @Id
    private Long id;

    @NotNull
    @Field(targetType = FieldType.INT64)
    private Long userId;

    @NotBlank
    @Size(max = 100)
    private String queryName;

    @NotBlank
    @Size(max = 500)
    private String queryDescription;

    @NotNull
    private EquitySearchRequest searchRequest;

    private Date updatedDate;
    private Date createdDate;

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

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getQueryDescription() {
        return queryDescription;
    }

    public void setQueryDescription(String queryDescription) {
        this.queryDescription = queryDescription;
    }



    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public EquitySearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(EquitySearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }
}
