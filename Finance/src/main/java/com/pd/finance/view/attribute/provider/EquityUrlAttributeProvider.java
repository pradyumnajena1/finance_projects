package com.pd.finance.view.attribute.provider;

import com.pd.finance.config.ApplicationContextUtil;
import com.pd.finance.model.Equity;
import com.pd.finance.service.DocumentService;
import com.pd.finance.view.attribute.provider.AbstractEquityViewAttributeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class EquityUrlAttributeProvider extends AbstractEquityViewAttributeProvider {
    private static final Logger logger = LoggerFactory.getLogger(EquityUrlAttributeProvider.class);

    public EquityUrlAttributeProvider( ) {

    }

    @Override
    public Object getAttributeValue(Equity equity) {
        String url = null;

        try {
            String localAddress = ApplicationContextUtil.getLocalAddress();
            int port = ApplicationContextUtil.getPort();
            String protocol = "http://";
            url = protocol + localAddress + ":" + port + "/equities/" + equity.getId();
        } catch (UnknownHostException e) {
           logger.error(e.getMessage());
        }
        return url;
    }
}
