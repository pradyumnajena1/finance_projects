package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquityDealsDetails extends EquityAttribute{

    @JsonProperty("blockDealDetails")
    private BlockDealDetails blockDealDetails;
    @JsonProperty("bulkDealDetails")
    private BulkDealDetails bulkDealDetails;

    public BlockDealDetails getBlockDealDetails() {
        return blockDealDetails;
    }

    public void setBlockDealDetails(BlockDealDetails blockDealDetails) {
        this.blockDealDetails = blockDealDetails;
    }

    public BulkDealDetails getBulkDealDetails() {
        return bulkDealDetails;
    }

    public void setBulkDealDetails(BulkDealDetails bulkDealDetails) {
        this.bulkDealDetails = bulkDealDetails;
    }
}
