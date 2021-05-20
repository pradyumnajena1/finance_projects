package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquityDeal {


    @JsonProperty("dealDate")
    private Date dealDate;


    @JsonProperty("dealType")
    private String dealType;

    @JsonProperty("traderName")
    private String traderName;

    @JsonProperty("tradedUnits")
    private BigInteger tradedUnits;

    @JsonProperty("tradedPercentage")
    private BigDecimal tradedPercentage;

    @JsonProperty("tradedPrice")
    private BigDecimal tradedPrice;

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    public BigInteger getTradedUnits() {
        return tradedUnits;
    }

    public void setTradedUnits(BigInteger tradedUnits) {
        this.tradedUnits = tradedUnits;
    }

    public BigDecimal getTradedPercentage() {
        return tradedPercentage;
    }

    public void setTradedPercentage(BigDecimal tradedPercentage) {
        this.tradedPercentage = tradedPercentage;
    }

    public BigDecimal getTradedPrice() {
        return tradedPrice;
    }

    public void setTradedPrice(BigDecimal tradedPrice) {
        this.tradedPrice = tradedPrice;
    }
}
