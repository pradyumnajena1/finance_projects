package com.pd.finance.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pd.finance.exceptions.ServiceException;
import com.pd.finance.model.EquityStockExchangeDetails;
import com.pd.finance.utils.JsonUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractHttpService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractHttpService.class);
   protected OkHttpClient httpClient;

   public AbstractHttpService() {
   }

    @NotNull
    final protected  OkHttpClient getHttpClient() {
       if(httpClient==null){
           httpClient =   doGetHttpClient();
       }
       return httpClient;
    }

    @NotNull
    protected OkHttpClient doGetHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        List<Interceptor> interceptors =  getInterceptors();
        for(Interceptor anInterceptor:interceptors){
            builder.addInterceptor(anInterceptor);
        }
         builder.connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS);

       return builder.build();
    }

    protected   List<Interceptor> getInterceptors(){
       return new ArrayList<>();
    }

    protected <T> T get(String url,Class<T> type) throws ServiceException {
        try {
            T value = null;
            String responseString = get(url);
            if(StringUtils.isNotBlank(responseString)){
                value  = JsonUtils.deserialize(responseString,type);
            }
            return value;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
           throw new ServiceException(e);
        }
    }

    protected String get(String url ) throws ServiceException {
        try {
            String responseString = null;
            Request request = new Request.Builder()
                    .url(url)

                    .build();

            try (Response response = getHttpClient().newCall(request).execute()) {
                if(response.isSuccessful()){
                    logger.info(MessageFormat.format("Successfully fetch url:{0} status code:{1}",url,response.code()));
                    responseString =  response.body().string();

                }else{
                    logger.error(MessageFormat.format("failed to fetch url:{0} status code:{1}",url,response.code()));
                }

            }
            return responseString;
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw new ServiceException(e);
        }
    }


}
