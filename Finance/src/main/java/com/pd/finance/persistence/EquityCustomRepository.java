package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;

public interface EquityCustomRepository {
    public   Page<Equity> searchEquity(Criteria criteria , Pageable pageable);
}
