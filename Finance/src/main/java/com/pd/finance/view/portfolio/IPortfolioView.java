package com.pd.finance.view.portfolio;

import com.pd.finance.model.Equity;
import com.pd.finance.model.portfolio.Portfolio;
import com.pd.finance.view.IView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public interface IPortfolioView extends IView  {

    public long getShares( );
    public Date getTradeDate(  );
    public BigDecimal getHighLimit(  );
    public BigDecimal getLowLimit(  );
    public BigDecimal getCostPerShare(  );
    public BigDecimal getMarketValue(  );
    public BigDecimal getTotalChange(  );
    public BigDecimal getTotalChangePercentage(  );
    public BigDecimal getDayChange(  );
    public BigDecimal getDayChangePercentage(  );
    public Set<String> getNotes(  );
    public long getNoOfLots(  );
    public BigDecimal getAnnualisedGains(  );
}
