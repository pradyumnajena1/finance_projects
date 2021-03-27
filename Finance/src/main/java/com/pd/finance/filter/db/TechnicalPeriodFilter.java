package com.pd.finance.filter.db;

import com.pd.finance.filter.EquityFilter;
import com.pd.finance.filter.FilterType;
import com.pd.finance.model.Equity;
import com.pd.finance.model.TechnicalDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;


public class TechnicalPeriodFilter  implements EquityFilter {
    private static final Logger logger = LoggerFactory.getLogger(TechnicalPeriodFilter.class);


    private TechnicalFilter dailyTechnicalFilter;
    private TechnicalFilter weeklyTechnicalFilter;
    private TechnicalFilter monthlyTechnicalFilter;

    public TechnicalFilter getDailyTechnicalFilter() {
        return dailyTechnicalFilter;
    }

    public void setDailyTechnicalFilter(TechnicalFilter dailyTechnicalFilter) {
        this.dailyTechnicalFilter = dailyTechnicalFilter;
    }

    public TechnicalFilter getWeeklyTechnicalFilter() {
        return weeklyTechnicalFilter;
    }

    public void setWeeklyTechnicalFilter(TechnicalFilter weeklyTechnicalFilter) {
        this.weeklyTechnicalFilter = weeklyTechnicalFilter;
    }

    public TechnicalFilter getMonthlyTechnicalFilter() {
        return monthlyTechnicalFilter;
    }

    public void setMonthlyTechnicalFilter(TechnicalFilter monthlyTechnicalFilter) {
        this.monthlyTechnicalFilter = monthlyTechnicalFilter;
    }
    public List<FieldError> validate(){
        List<FieldError> errors = new ArrayList<>();
        if(dailyTechnicalFilter!=null){
            errors.addAll(dailyTechnicalFilter.validate());
        }
        if(weeklyTechnicalFilter!=null){
            errors.addAll(weeklyTechnicalFilter.validate());
        }
        if(monthlyTechnicalFilter!=null){
            errors.addAll(monthlyTechnicalFilter.validate());
        }
        return errors;
    }

    @Override
    public FilterType getFilterType() {
        return FilterType.InDb;
    }

    @Override
    public Criteria getCriteria(String parentObject) {
        List<Criteria> criteriaList = new ArrayList<>();

        if(dailyTechnicalFilter!=null){
            criteriaList.add(dailyTechnicalFilter.getCriteria(parentObject+".daily"));
        }

        if(monthlyTechnicalFilter!=null){
            criteriaList.add(monthlyTechnicalFilter.getCriteria( parentObject+".monthly"));
        }
        if(weeklyTechnicalFilter!=null){
            criteriaList.add(weeklyTechnicalFilter.getCriteria( parentObject+".weekly"));
        }



        Criteria criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));
        return criteria;
    }

    @Override
    public boolean apply(Equity equity) {

        TechnicalDetails technicalDetails = equity.getTechnicalDetails();

        if(technicalDetails==null){
            return false;
        }
        if(dailyTechnicalFilter!=null){
            logger.info("isValidEquity DailyTechnicalFilter didn't matched for equity {}",equity.getEquityIdentifiers());
            boolean valid = dailyTechnicalFilter.apply(technicalDetails.getDaily());
            if(!valid) {
                return false;
            }
        }

        if(monthlyTechnicalFilter!=null){
            boolean valid = monthlyTechnicalFilter.apply(technicalDetails.getMonthly());
            if(!valid){
                logger.info("isValidEquity MonthlyTechnicalFilter didn't matched for equity {}",equity.getEquityIdentifiers());
                return false;
            }
        }

        if(weeklyTechnicalFilter!=null){
            boolean valid = weeklyTechnicalFilter.apply(technicalDetails.getWeekly());
            if(!valid){
                logger.info("isValidEquity WeeklyTechnicalFilter didn't matched for equity {}",equity.getEquityIdentifiers());
                return false;
            }
        }



        return true;
    }
}
