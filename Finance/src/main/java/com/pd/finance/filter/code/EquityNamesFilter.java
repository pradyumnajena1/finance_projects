package com.pd.finance.filter.code;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.SourceDetails;
import com.pd.finance.utils.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.HashSet;
import java.util.Set;

public class EquityNamesFilter implements EquityFilter {

    public static final String DefaultSource = Constants.SOURCE_MONEY_CONTROL;
    private String source = DefaultSource;
    private Set<String> names = new HashSet<>();


    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        Criteria elemMatchCriteria =  Criteria.where("EquityName").in(names).andOperator(Criteria.where("sourceName").is(source));
        Criteria criteria = Criteria.where("sourceDetails.sourceDetails").elemMatch(elemMatchCriteria);

        return criteria;
    }

    @Override
    public boolean apply(Equity equity) {
        if(equity.getSourceDetails()==null){
            return false;
        }
        SourceDetails sourceDetails = equity.getSourceDetails().getSourceDetails(source);
        if(sourceDetails==null){
            return false;
        }
        if(!names.contains( sourceDetails.getEquityName())){
            return false;
        }
        return true;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        if(StringUtils.isBlank(source)){
            source = DefaultSource;
        }
        this.source = source;
    }

    public Set<String> getNames() {
        return names;
    }

    public void setNames(Set<String> names) {
        this.names = names;
    }
}
