package com.pd.finance.view.equity;

public enum EquityViewMovingAvgAttribute {
    FiftyDMA("FiftyDMA","50-DMA"),
    FiftyDMAChange("FiftyDMAChange","50-DMA chg"),
    FiftyDMAChangePercentage("FiftyDMAChangePercentage","50-DMA chg %"),
    TwoHundredDMA("TwoHundredDMA","200-DMA"),
    TwoHundredDMAChange("TwoHundredDMAChange","200-DMA chg"),
    TwoHundredDMAChangePercentage("TwoHundredDMAChangePercentage","200-DMA chg %"),
    ;

    private final String name;
    private final String displayName;

    EquityViewMovingAvgAttribute(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
    EquityViewMovingAvgAttribute(String name ) {
        this.name = name;
        this.displayName = name;
    }
}
