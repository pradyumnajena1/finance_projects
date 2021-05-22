package com.pd.finance.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.model.Equity;
import com.pd.finance.view.IViewAttribute;
import com.pd.finance.view.equity.EquitySearchView;
import com.pd.finance.view.equity.provider.EquityViewProvider;
import com.pd.finance.view.equity.provider.IEquityViewProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquitySearchResponse {
    @JsonIgnore
    private List<Equity> equities;
    private List<Map<String,IViewAttribute<Equity>>> equitySearchViews;

    public EquitySearchResponse(List<Equity> equities) {
        this.equities = equities;
        this.equitySearchViews = getEquitySearchViews();
    }
    @JsonProperty("equities")
    public List<Map<String,IViewAttribute<Equity>>> getEquities() {
        return equitySearchViews;
    }

    @NotNull
    private List<Map<String,IViewAttribute<Equity>>> getEquitySearchViews() {
        List<Map<String,IViewAttribute<Equity>>> values = new ArrayList<>();
        equities.stream().forEach(equity -> {
            Map<String,IViewAttribute<Equity>> attributeMap = getAttributeMap(equity);
            values.add(attributeMap);
        });

        return values;
    }

    private  Map<String,IViewAttribute<Equity>> getAttributeMap(Equity equity) {
        IEquityViewProvider equityViewProvider = new EquityViewProvider(equity);
        EquitySearchView  equitySearchView = new EquitySearchView(equityViewProvider);
        Map<String,IViewAttribute<Equity>> attributeMap = equitySearchView.getAttributeMap();
        return attributeMap;
    }


}
