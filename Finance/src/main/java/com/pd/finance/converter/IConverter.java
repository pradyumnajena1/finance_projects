package com.pd.finance.converter;

public interface  IConverter<U,V> {
    public V convert(U u);
}
