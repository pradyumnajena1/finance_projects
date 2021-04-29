package com.pd.finance.model.equity.summary;

import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

public class Trend {

    private String period;

    @Field(targetType = FieldType.DOUBLE)
    private Long strongBuy;

    @Field(targetType = FieldType.DOUBLE)
    private Long buy;

    @Field(targetType = FieldType.DOUBLE)
    private Long hold;

    @Field(targetType = FieldType.DOUBLE)
    private Long sell;

    @Field(targetType = FieldType.DOUBLE)
    private Long strongSell;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getStrongBuy() {
        return strongBuy;
    }

    public void setStrongBuy(Long strongBuy) {
        this.strongBuy = strongBuy;
    }

    public Long getBuy() {
        return buy;
    }

    public void setBuy(Long buy) {
        this.buy = buy;
    }

    public Long getHold() {
        return hold;
    }

    public void setHold(Long hold) {
        this.hold = hold;
    }

    public Long getSell() {
        return sell;
    }

    public void setSell(Long sell) {
        this.sell = sell;
    }

    public Long getStrongSell() {
        return strongSell;
    }

    public void setStrongSell(Long strongSell) {
        this.strongSell = strongSell;
    }

    public long getTotalRecommendations( ) {
        long numRecommendations = 0;
        Long strongBuy =  getStrongBuy();
        if(strongBuy!=null){

            numRecommendations = numRecommendations + strongBuy;
        }

        Long buy =  getBuy();
        if(buy!=null){

            numRecommendations = numRecommendations + buy;
        }


        Long strongSell =  getStrongSell();
        if(strongSell!=null){

            numRecommendations = numRecommendations + strongSell;
        }

        Long sell =  getSell();
        if(sell!=null){

            numRecommendations = numRecommendations + sell;
        }
        Long hold =  getHold();
        if(hold!=null){

            numRecommendations = numRecommendations + hold;
        }
        return  numRecommendations;
    }
}
