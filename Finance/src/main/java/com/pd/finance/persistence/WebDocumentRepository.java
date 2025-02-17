package com.pd.finance.persistence;

import com.pd.finance.model.Equity;
import com.pd.finance.model.WebDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebDocumentRepository  extends MongoRepository<WebDocument, String>,WebDocumentCustomRepository {

    @Query(value="{ 'url' : ?0 }")
    public List<WebDocument> findByUrl(String name);
}
