package com.pd.finance.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class EquityIdentifier {

    private String id;
    private String name;
    private String bseId;
    private String nseId;

    private Map<String,Object> additionalAttributes = new HashMap<>();

    public EquityIdentifier(String id, String name, String bseId, String nseId) {
        this.id = id;
        this.name = name;
        this.bseId = bseId;
        this.nseId = nseId;
    }
    public EquityIdentifier( String name) {

        this.name = name;

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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBseId(String bseId) {
        this.bseId = bseId;
    }

    public void setNseId(String nseId) {
        this.nseId = nseId;
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
    public void putAdditionalAttribute(String key,Object value){
        additionalAttributes.put(key,value);
    }
    public Object getAdditionalAttribute(String key){
        return additionalAttributes.get(key);
    }
}
