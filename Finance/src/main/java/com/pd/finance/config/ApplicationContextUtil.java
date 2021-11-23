package com.pd.finance.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ApplicationContextUtil implements ApplicationContextAware, ApplicationListener<WebServerInitializedEvent> {
    private static ApplicationContext applicationContext;
    private static Integer port;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName,Class<T> clazz){
        return applicationContext.getBean(beanName,clazz);
    }
    public static <T> T getBean( Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
    public static int getPort(){
        return  port;
    }

    public static String getLocalAddress() throws UnknownHostException {

          return  InetAddress.getLocalHost().getHostAddress();

    }
    public static String getRemoteAddress() throws UnknownHostException {

        return  InetAddress.getLoopbackAddress().getHostAddress();

    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        Integer port = webServerInitializedEvent.getWebServer().getPort();
        ApplicationContextUtil.port = port;
    }
}
