package com.pd.finance.response.chart;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "chart"
})

public class ChartResponse {


    @JsonProperty("chart")
    private Chart chart;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("chart")
    public Chart getChart() {
        return chart;
    }

    @JsonProperty("chart")
    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


    public BigDecimal getPercentageGain() {
        BigDecimal percentageGain = null;
        BigDecimal prevClose = getPrevClose();
        if(prevClose!=null){

            percentageGain = getChange().divide(prevClose).multiply(new BigDecimal(100));
        }
        return percentageGain;
    }

    public BigDecimal getChange() {
        BigDecimal change = null;
        BigDecimal lastPrice = getLastPrice();
        if(lastPrice!=null){

              change = lastPrice.subtract(getPrevClose());
        }
        return change;
    }


    public BigDecimal getPrevClose() {
        BigDecimal prevClose  = null;
        Result result = getResult();
        if(result!=null){
            Meta meta = result.getMeta();
            if(meta!=null){
                Double previousClose = meta.getPreviousClose();
                prevClose = new BigDecimal(previousClose);
            }

        }

        return prevClose;
    }

    public BigDecimal getLastPrice() {
        Quote quote = getQuote();
        if(quote==null){
            return null;
        }
        List<Double> closes =  quote.getClose();
        if(CollectionUtils.isEmpty(closes)){
            return null;
        }


        Double last = null;
        for(int i = closes.size()-1;i>=0;i--){
            Double closeInstance = closes.get(i);
            if(closeInstance!=null){
                last = closeInstance;
                break;
            }
        }
        return  new BigDecimal( last);
    }

    public BigDecimal getHigh() {
        Quote quote = getQuote();
        if(quote==null){
            return null;
        }
        List<Double> highs =  quote.getHigh();
        if(CollectionUtils.isEmpty(highs)){
            return null;
        }


        Double max  = Double.MIN_VALUE;
        for(Double high:highs){
            if( high!=null && high.compareTo(max)>0){
                max = high;
            }
        }
        return new BigDecimal(max);
    }

    protected Result getResult() {

        Chart chart = getChart();
        if(chart ==null){
            return null;
        }
        List<Result> result = chart.getResult();
        if(CollectionUtils.isEmpty(result)){
            return null;
        }
        return result.get(0);
    }

    public BigDecimal getLow() {
        Quote quote = getQuote();
        if(quote==null){
            return null;
        }
        List<Double> lows =  quote.getLow();
        if(CollectionUtils.isEmpty(lows)){
            return null;
        }

        Double min  = Double.MAX_VALUE;
        for(Double low:lows){
            if(low!=null && low.compareTo(min)<0){
                min = low;
            }
        }
        return new BigDecimal(min);
    }

    protected  Quote getQuote() {
        Result result = getResult();
        if(result==null){
            return null;
        }
        Indicators indicators = result.getIndicators();
        if(indicators==null){
            return null;
        }
        List<Quote> quotes = indicators.getQuote();
        if(CollectionUtils.isEmpty(quotes)){
            return null;
        }
        return quotes.get(0);
    }

}
