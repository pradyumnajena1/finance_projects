package com.pd.finance.model;

import java.util.Date;

public abstract class EquityAttribute {

    private String source;

    private Date updatedDate;


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
}
