package com.pd.finance.persistence;

import com.pd.finance.exceptions.PersistenceException;

public interface WebDocumentCustomRepository {
    void removeExpiredWebDocuments() throws PersistenceException;
}
