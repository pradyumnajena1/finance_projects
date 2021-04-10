
package com.pd.finance.response.summary;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "maxAge",
    "priceHint",
    "enterpriseValue",
    "forwardPE",
    "profitMargins",
    "floatShares",
    "sharesOutstanding",
    "heldPercentInsiders",
    "heldPercentInstitutions",
    "bookValue",
    "priceToBook",
    "trailingEps",
    "forwardEps",
    "enterpriseToRevenue",
    "enterpriseToEbitda",
    "52WeekChange",
    "SandP52WeekChange"
})
@Generated("jsonschema2pojo")
public class DefaultKeyStatistics {

    @JsonProperty("maxAge")
    private Long maxAge;
    @JsonProperty("priceHint")
    private PriceHint priceHint;
    @JsonProperty("enterpriseValue")
    private EnterpriseValue enterpriseValue;
    @JsonProperty("forwardPE")
    private ForwardPE forwardPE;
    @JsonProperty("profitMargins")
    private ProfitMargins profitMargins;
    @JsonProperty("floatShares")
    private FloatShares floatShares;
    @JsonProperty("sharesOutstanding")
    private SharesOutstanding sharesOutstanding;
    @JsonProperty("heldPercentInsiders")
    private HeldPercentInsiders heldPercentInsiders;
    @JsonProperty("heldPercentInstitutions")
    private HeldPercentInstitutions heldPercentInstitutions;
    @JsonProperty("bookValue")
    private BookValue bookValue;
    @JsonProperty("priceToBook")
    private PriceToBook priceToBook;
    @JsonProperty("trailingEps")
    private TrailingEps trailingEps;
    @JsonProperty("forwardEps")
    private ForwardEps forwardEps;
    @JsonProperty("enterpriseToRevenue")
    private EnterpriseToRevenue enterpriseToRevenue;
    @JsonProperty("enterpriseToEbitda")
    private EnterpriseToEbitda enterpriseToEbitda;
    @JsonProperty("52WeekChange")
    private FiftyTwoWeekChange FiftyTwoWeekChange;
    @JsonProperty("SandP52WeekChange")
    private SandP52WeekChange sandP52WeekChange;

    @JsonProperty("maxAge")
    public Long getMaxAge() {
        return maxAge;
    }

    @JsonProperty("maxAge")
    public void setMaxAge(Long maxAge) {
        this.maxAge = maxAge;
    }

    @JsonProperty("priceHint")
    public PriceHint getPriceHint() {
        return priceHint;
    }

    @JsonProperty("priceHint")
    public void setPriceHint(PriceHint priceHint) {
        this.priceHint = priceHint;
    }

    @JsonProperty("enterpriseValue")
    public EnterpriseValue getEnterpriseValue() {
        return enterpriseValue;
    }

    @JsonProperty("enterpriseValue")
    public void setEnterpriseValue(EnterpriseValue enterpriseValue) {
        this.enterpriseValue = enterpriseValue;
    }

    @JsonProperty("forwardPE")
    public ForwardPE getForwardPE() {
        return forwardPE;
    }

    @JsonProperty("forwardPE")
    public void setForwardPE(ForwardPE forwardPE) {
        this.forwardPE = forwardPE;
    }

    @JsonProperty("profitMargins")
    public ProfitMargins getProfitMargins() {
        return profitMargins;
    }

    @JsonProperty("profitMargins")
    public void setProfitMargins(ProfitMargins profitMargins) {
        this.profitMargins = profitMargins;
    }

    @JsonProperty("floatShares")
    public FloatShares getFloatShares() {
        return floatShares;
    }

    @JsonProperty("floatShares")
    public void setFloatShares(FloatShares floatShares) {
        this.floatShares = floatShares;
    }

    @JsonProperty("sharesOutstanding")
    public SharesOutstanding getSharesOutstanding() {
        return sharesOutstanding;
    }

    @JsonProperty("sharesOutstanding")
    public void setSharesOutstanding(SharesOutstanding sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    @JsonProperty("heldPercentInsiders")
    public HeldPercentInsiders getHeldPercentInsiders() {
        return heldPercentInsiders;
    }

    @JsonProperty("heldPercentInsiders")
    public void setHeldPercentInsiders(HeldPercentInsiders heldPercentInsiders) {
        this.heldPercentInsiders = heldPercentInsiders;
    }

    @JsonProperty("heldPercentInstitutions")
    public HeldPercentInstitutions getHeldPercentInstitutions() {
        return heldPercentInstitutions;
    }

    @JsonProperty("heldPercentInstitutions")
    public void setHeldPercentInstitutions(HeldPercentInstitutions heldPercentInstitutions) {
        this.heldPercentInstitutions = heldPercentInstitutions;
    }

    @JsonProperty("bookValue")
    public BookValue getBookValue() {
        return bookValue;
    }

    @JsonProperty("bookValue")
    public void setBookValue(BookValue bookValue) {
        this.bookValue = bookValue;
    }

    @JsonProperty("priceToBook")
    public PriceToBook getPriceToBook() {
        return priceToBook;
    }

    @JsonProperty("priceToBook")
    public void setPriceToBook(PriceToBook priceToBook) {
        this.priceToBook = priceToBook;
    }

    @JsonProperty("trailingEps")
    public TrailingEps getTrailingEps() {
        return trailingEps;
    }

    @JsonProperty("trailingEps")
    public void setTrailingEps(TrailingEps trailingEps) {
        this.trailingEps = trailingEps;
    }

    @JsonProperty("forwardEps")
    public ForwardEps getForwardEps() {
        return forwardEps;
    }

    @JsonProperty("forwardEps")
    public void setForwardEps(ForwardEps forwardEps) {
        this.forwardEps = forwardEps;
    }

    @JsonProperty("enterpriseToRevenue")
    public EnterpriseToRevenue getEnterpriseToRevenue() {
        return enterpriseToRevenue;
    }

    @JsonProperty("enterpriseToRevenue")
    public void setEnterpriseToRevenue(EnterpriseToRevenue enterpriseToRevenue) {
        this.enterpriseToRevenue = enterpriseToRevenue;
    }

    @JsonProperty("enterpriseToEbitda")
    public EnterpriseToEbitda getEnterpriseToEbitda() {
        return enterpriseToEbitda;
    }

    @JsonProperty("enterpriseToEbitda")
    public void setEnterpriseToEbitda(EnterpriseToEbitda enterpriseToEbitda) {
        this.enterpriseToEbitda = enterpriseToEbitda;
    }

    @JsonProperty("52WeekChange")
    public FiftyTwoWeekChange get52WeekChange() {
        return FiftyTwoWeekChange;
    }

    @JsonProperty("52WeekChange")
    public void set52WeekChange(FiftyTwoWeekChange FiftyTwoWeekChange) {
        this.FiftyTwoWeekChange = FiftyTwoWeekChange;
    }

    @JsonProperty("SandP52WeekChange")
    public SandP52WeekChange getSandP52WeekChange() {
        return sandP52WeekChange;
    }

    @JsonProperty("SandP52WeekChange")
    public void setSandP52WeekChange(SandP52WeekChange sandP52WeekChange) {
        this.sandP52WeekChange = sandP52WeekChange;
    }


}
