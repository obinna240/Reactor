package com.example.emp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return null;
    }
}
