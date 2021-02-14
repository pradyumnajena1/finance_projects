package com.pd.finance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.utils.JsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public abstract class AbstractHttpService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractHttpService.class);
   private OkHttpClient httpClient;

   public AbstractHttpService(){
       httpClient = new OkHttpClient();
   }
    protected <T> T get(String url,Class<T> type) throws ServiceException {
        try {
            T value = null;
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {

                String responseString =  response.body().string();
                value = JsonUtils.deserialize(responseString, type);
            }
            return value;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
           throw new ServiceException(e);
        }
    }


}
