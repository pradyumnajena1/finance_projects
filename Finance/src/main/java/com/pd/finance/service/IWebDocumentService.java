package com.pd.finance.service;

import com.pd.finance.exceptions.ServiceException;

public interface IWebDocumentService {
    void removeExpiredWebDocuments() throws ServiceException;
}
