package com.pd.finance.model;

import java.util.Date;

public abstract class EquityAttribute {

    private String source;

    private Date updatedDate;

    private Date createdDate;


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
}
