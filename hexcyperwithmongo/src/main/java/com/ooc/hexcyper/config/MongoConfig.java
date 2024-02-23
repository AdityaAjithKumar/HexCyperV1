package com.ooc.hexcyper.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.ooc.hexcyper.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String baseUri;

    @Override
    protected String getDatabaseName() {
        return "chatHistoryDB"; // Your MongoDB database name
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(baseUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public boolean autoIndexCreation() {
        return true;
    }
}
