package com.pd.finance.view;

public interface IViewAttribute<T> {
    String getAttributeName();
    String getAttributeDisplayName();
    Object getValue(T t);
    Object getAttributeValue();
    void setAttributeValue(Object object);

}
