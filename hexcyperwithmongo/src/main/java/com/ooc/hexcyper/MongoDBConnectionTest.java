package com.ooc.hexcyper;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnectionTest {

    public static void main(String[] args) {
        String connectionString = "mongodb+srv://alphagamers456:alphagamers456@response.yyefbmo.mongodb.net/?retryWrites=true&w=majority";

        try {
            ConnectionString connString = new ConnectionString(connectionString);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .build();
            var mongoClient = MongoClients.create(settings);

            // Access the 'Response' database
            MongoDatabase database = mongoClient.getDatabase("Response");

            // Access a collection. If the collection doesn't exist, MongoDB will create it for you
            MongoCollection<Document> collection = database.getCollection("ChatResponse");

            // Create a new document with the message "Hello World"
            Document doc = new Document("message", "Hello World");

            // Insert the document into the collection
            collection.insertOne(doc);

            System.out.println("Document inserted successfully.");

            // Close the MongoClient when done
            mongoClient.close();
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB. Error: " + e.getMessage());
        }
    }
}
