package com.pd.finance.view.attribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.model.Equity;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.attribute.provider.IEquityViewAttributeProvider;

import java.util.Objects;

public class BaseEquityViewAttribute implements IViewAttribute<Equity> {
    @JsonIgnore
    private IEquityViewAttributeProvider attributeProvider;

    @JsonProperty("attributeName")
    private String name;
    @JsonProperty("attributeDisplayName")
    private  String displayName;
    @JsonProperty("attributeValue")
    private Object value;


    public BaseEquityViewAttribute(IEquityViewAttributeProvider attributeProvider, String name, String displayName) {
        this.attributeProvider = attributeProvider;
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    @JsonIgnore
    public String getAttributeName() {
        return name;
    }

    @Override
    @JsonIgnore
    public String getAttributeDisplayName() {
        return displayName;
    }

    @Override
    @JsonIgnore
    public Object getValue(Equity equity) {
        return null;
    }

    @Override
    @JsonIgnore
    public Object getAttributeValue() {
        return value;
    }

    @Override
    public void setAttributeValue(Object object) {
           this.value = object;
    }

    public IEquityViewAttributeProvider getAttributeProvider() {
        return attributeProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEquityViewAttribute that = (BaseEquityViewAttribute) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
