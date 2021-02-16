package com.pd.finance.service;

import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;

@Component
public class RateLimitInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    private int waitTimeInMillis;

    public RateLimitInterceptor(@Value("${waitTimeInMillis}") int waitTimeInMillis) {
        this.waitTimeInMillis = waitTimeInMillis;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if(!response.isSuccessful() && isThrottledResponse(response)){
            logger.error(MessageFormat.format("response : {}",response.message()));
            try {
                logger.error(MessageFormat.format("wait and retry after : {} millis ",waitTimeInMillis));
                Thread.sleep(waitTimeInMillis);
            } catch (InterruptedException e) {
               logger.error(e.getMessage(),e);
            }
            response=chain.proceed(chain.request());
        }
        return response;
    }

    protected boolean isThrottledResponse(Response response) {
        return response.code() == 429;
    }
}
