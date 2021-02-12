package com.pd.finance.model;

import org.apache.commons.lang.StringUtils;

public class EquityIdentifier {

    private String id;
    private String name;
    private String bseId;
    private String nseId;

    public EquityIdentifier(String id, String name, String bseId, String nseId) {
        this.id = id;
        this.name = name;
        this.bseId = bseId;
        this.nseId = nseId;
    }

    public String getId() {
        return id;
    }

    public String getBseId() {
        return bseId;
    }

    public String getNseId() {
        return nseId;
    }

    public String getName() {
        return name;
    }

    public String getSearchString(){

        if(StringUtils.isNotBlank(bseId)&&!StringUtils.isNumeric(bseId)){
            return bseId;
        }
        if(StringUtils.isNotBlank(nseId)&&!StringUtils.isNumeric(nseId)){
            return nseId;
        }
        return name;
    }
}
