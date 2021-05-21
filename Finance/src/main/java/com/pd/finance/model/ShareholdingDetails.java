package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShareholdingDetails extends EquityAttribute{

    @JsonProperty("promoterShareholding")
    private PromoterShareholding promoterShareholding;

    @JsonProperty("fiisShareholding")
    private FIISShareholding fiisShareholding;

    @JsonProperty("diisShareholding")
    private DIISShareholding diisShareholding;

    @JsonProperty("governmentShareholding")
    private GovernmentShareholding governmentShareholding;

    @JsonProperty("publicShareholding")
    private PublicShareholding publicShareholding;

    public PromoterShareholding getPromoterShareholding() {
        return promoterShareholding;
    }

    public void setPromoterShareholding(PromoterShareholding promoterShareholding) {
        this.promoterShareholding = promoterShareholding;
    }

    public FIISShareholding getFiisShareholding() {
        return fiisShareholding;
    }

    public void setFiisShareholding(FIISShareholding fiisShareholding) {
        this.fiisShareholding = fiisShareholding;
    }

    public DIISShareholding getDiisShareholding() {
        return diisShareholding;
    }

    public void setDiisShareholding(DIISShareholding diisShareholding) {
        this.diisShareholding = diisShareholding;
    }

    public GovernmentShareholding getGovernmentShareholding() {
        return governmentShareholding;
    }

    public void setGovernmentShareholding(GovernmentShareholding governmentShareholding) {
        this.governmentShareholding = governmentShareholding;
    }

    public PublicShareholding getPublicShareholding() {
        return publicShareholding;
    }

    public void setPublicShareholding(PublicShareholding publicShareholding) {
        this.publicShareholding = publicShareholding;
    }
}
