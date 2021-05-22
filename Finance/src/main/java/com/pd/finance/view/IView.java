package com.pd.finance.view;

import java.util.Map;

public interface IView<T> {
    public Map<String,IViewAttribute<T>> getAttributeMap();
}
