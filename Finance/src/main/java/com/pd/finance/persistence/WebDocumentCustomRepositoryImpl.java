package com.pd.finance.persistence;

import com.pd.finance.exceptions.PersistenceException;
import com.pd.finance.model.WebDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebDocumentCustomRepositoryImpl implements WebDocumentCustomRepository{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public WebDocumentCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void removeExpiredWebDocuments() throws PersistenceException {
        try {
            Query query1 = new Query();
            List<Criteria> criteriaList = new ArrayList<>();
            criteriaList.add(Criteria.where("expireDate").exists(true));
            criteriaList.add(Criteria.where("expireDate").lt(new Date()));

            Criteria criteria = new Criteria().andOperator(criteriaList.toArray( new Criteria[criteriaList.size()]));

            query1.addCriteria(criteria);
            mongoTemplate.remove(query1, WebDocument.class);
        } catch (Exception exception) {
            throw new PersistenceException(exception.getMessage(),exception);
        }
    }
}
