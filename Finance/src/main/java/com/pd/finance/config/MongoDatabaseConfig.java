package com.pd.finance.config;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.lang.NonNull;
import org.bson.types.Decimal128;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.pd.finance")
public class MongoDatabaseConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "finance";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/finance");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.pd.finance");
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new BigDecimalDecimal128Converter(),
                new Decimal128BigDecimalConverter()
        ));

    }

    @WritingConverter
    private static class BigDecimalDecimal128Converter implements Converter<BigDecimal, Decimal128> {

        @Override
        public Decimal128 convert(@NonNull BigDecimal source) {
            return new Decimal128(source);
        }
    }

    @ReadingConverter
    private static class Decimal128BigDecimalConverter implements Converter<Decimal128, BigDecimal> {

        @Override
        public BigDecimal convert(@NonNull Decimal128 source) {
            return source.bigDecimalValue();
        }

    }
}
