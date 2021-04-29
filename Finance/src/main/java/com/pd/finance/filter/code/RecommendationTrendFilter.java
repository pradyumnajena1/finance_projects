package com.pd.finance.filter.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.equity.summary.EquitySummary;
import com.pd.finance.model.equity.summary.RecommendationTrend;
import com.pd.finance.model.equity.summary.Trend;
import com.pd.finance.request.EquitySearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class RecommendationTrendFilter implements EquityFilter {

    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(RecommendationTrendFilter.class);


    @JsonProperty("minStrongBuyPercentage")
    private BigDecimal minStrongBuyPercentage;

    @JsonProperty("minBuyPercentage")
    private BigDecimal minBuyPercentage;

    @JsonProperty("maxSellPercentage")
    private BigDecimal maxSellPercentage;

    @JsonProperty("maxStrongSellPercentage")
    private BigDecimal maxStrongSellPercentage;

    @JsonProperty("minNumMonths")
    @Max(value = 3)
    @Min(value = 1)
    private Integer minNumMonths = 3;


    @Override
    public FilterType getFilterType() {
        return FilterType.InCode;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        return null;
    }

    @Override
    public boolean apply(Equity equity) {
        try {
            EquitySummary equitySummary = equity.getEquitySummary();
            if (equitySummary == null) {
                return false;
            }


            RecommendationTrend recommendationTrend = equitySummary.getRecommendationTrend();
            if (recommendationTrend == null) {
                return false;
            }
            List<Trend> trends = recommendationTrend.getTrend();
            if (trends == null || trends.size() <= minNumMonths) {
                return false;
            }
            for (int i = 1; i <= minNumMonths; i++) {
                Trend currentTrend = trends.get(i);
                if (!isValidTrend(currentTrend)) {
                    return false;
                }

            }

            logger.info("apply exec completed for equity {}", equity.getDefaultEquityIdentifier());
            return true;


        } catch (Exception exception) {
            logger.error("apply exec failed for equity {}", equity.getDefaultEquityIdentifier());
            logger.error(exception.getMessage());
            return false;
        }
    }

    private boolean isValidTrend(Trend trend) {
        try {
            if (trend == null) {
                return false;
            }
            BigDecimal totalRecommendations = new BigDecimal(trend.getTotalRecommendations());
            if (!isValidStrongBuyPercentage(trend, totalRecommendations)) {
                return false;
            }
            if (!isValidBuyPercentage(trend, totalRecommendations)) {
                return false;
            }
            if (!isValidStrongSellPercentage(trend, totalRecommendations)) {
                return false;
            }
            if (!isValidSellPercentage(trend, totalRecommendations)) {
                return false;
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }

    private boolean isValidSellPercentage(Trend trend, BigDecimal totalRecommendations) {
        try {
            BigDecimal sellPercentage = null;
            if (maxSellPercentage != null) {

                Long sell = trend.getSell();
                if (sell == null || sell.equals(Long.valueOf(0))) {
                    return false;
                }
                sellPercentage = new BigDecimal(sell).divide(totalRecommendations,2, RoundingMode.HALF_UP);
                if (sellPercentage.compareTo(maxSellPercentage) > 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }

    private boolean isValidStrongSellPercentage(Trend currentTrend, BigDecimal totalRecommendations) {
        try {
            BigDecimal strongSellPercentage = null;
            if (maxStrongSellPercentage != null) {

                Long strongSell = currentTrend.getStrongSell();
                if (strongSell == null || strongSell.equals(Long.valueOf(0))) {
                    return false;
                }
                strongSellPercentage = new BigDecimal(strongSell).divide(totalRecommendations,2, RoundingMode.HALF_UP);
                if (strongSellPercentage.compareTo(maxStrongSellPercentage) > 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }

    private boolean isValidBuyPercentage(Trend currentTrend, BigDecimal totalRecommendations) {
        try {
            BigDecimal buyPercentage = null;
            if (minBuyPercentage != null) {

                Long buy = currentTrend.getBuy();
                if (buy == null || buy.equals(Long.valueOf(0))) {
                    return false;
                }
                  buyPercentage = new BigDecimal(buy).divide(totalRecommendations,2, RoundingMode.HALF_UP);
                if (buyPercentage.compareTo(minBuyPercentage) < 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }

    protected boolean isValidStrongBuyPercentage(Trend currentTrend, BigDecimal totalRecommendations) {
        try {
            BigDecimal strongBuyPercentage = null;
            if (minStrongBuyPercentage != null) {

                Long strongBuy = currentTrend.getStrongBuy();
                if (strongBuy == null || strongBuy.equals(Long.valueOf(0))) {
                    return false;
                }
                strongBuyPercentage = new BigDecimal(strongBuy).divide(totalRecommendations,2, RoundingMode.HALF_UP);
                if (strongBuyPercentage.compareTo(minStrongBuyPercentage) < 0) {
                    return false;
                }
            }
            return true;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return false;
        }
    }


}
