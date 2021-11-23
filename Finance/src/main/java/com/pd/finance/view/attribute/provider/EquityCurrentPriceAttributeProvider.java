package com.pd.finance.view.attribute.provider;

import com.pd.finance.model.Equity;
import com.pd.finance.model.EquityCurrentPriceStats;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class EquityCurrentPriceAttributeProvider extends AbstractEquityViewAttributeProvider{


    @Override
    public Object getAttributeValue(Equity equity) {
        CurrentPriceAttribute currentPriceAttribute = new CurrentPriceAttribute();
        EquityCurrentPriceStats equityCurrentPriceStats = equity.getEquityCurrentPriceStats();
        if (equityCurrentPriceStats!=null) {
            BigDecimal lastPrice = equityCurrentPriceStats.getLastPrice();
            if (lastPrice!=null) {
                lastPrice= lastPrice.setScale(2, RoundingMode.HALF_UP);
                currentPriceAttribute.setCurrentPrice(lastPrice);
            }
            BigDecimal prevClose = equityCurrentPriceStats.getPrevClose();
            if (prevClose!=null) {
                prevClose= prevClose.setScale(2, RoundingMode.HALF_UP);
                currentPriceAttribute.setPreviousClose(prevClose);
            }
            BigDecimal percentageGain = equityCurrentPriceStats.getPercentageGain();
            if (percentageGain!=null) {
                percentageGain= percentageGain.setScale(2, RoundingMode.HALF_UP);
                currentPriceAttribute.setChangePercentage(percentageGain);
            }
        }
        return currentPriceAttribute;
    }

    public static class CurrentPriceAttribute{
        private BigDecimal currentPrice;
        private BigDecimal previousClose;
        private BigDecimal changePercentage;

        public BigDecimal getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(BigDecimal currentPrice) {
            this.currentPrice = currentPrice;
        }

        public BigDecimal getPreviousClose() {
            return previousClose;
        }

        public void setPreviousClose(BigDecimal previousClose) {
            this.previousClose = previousClose;
        }

        public BigDecimal getChangePercentage() {
            return changePercentage;
        }

        public void setChangePercentage(BigDecimal changePercentage) {
            this.changePercentage = changePercentage;
        }
    }
}
