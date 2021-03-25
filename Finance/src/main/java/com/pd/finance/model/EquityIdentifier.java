package com.pd.finance.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EquityIdentifier {

    private String id;
    private String name;
    private String exchange;
    private String symbol;
    private String source;

    private Map<String,Object> additionalAttributes = new HashMap<>();

    public EquityIdentifier(String id, String name, String exchange, String symbol, String source) {
        this.id = id;
        this.name = name;
        this.exchange = exchange;
        this.symbol = symbol;
        this.source = source;
    }

    public EquityIdentifier(String name, String exchange, String source) {
        this.name = name;
        this.exchange = exchange;
        this.source = source;
    }

    public String getId() {
        return id;
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

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSearchString(){


        return name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void putAdditionalAttribute(String key, Object value){
        additionalAttributes.put(key,value);
    }
    public Object getAdditionalAttribute(String key){
        return additionalAttributes.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquityIdentifier that = (EquityIdentifier) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                exchange.equals(that.exchange) &&
                source.equals(that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, exchange, source);
    }

    @Override
    public String toString() {
        return "EquityIdentifier{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", exchange='" + exchange + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
