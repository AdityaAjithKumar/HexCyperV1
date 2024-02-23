package com.ooc.hexcyper.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String googleId;
    private String username;

    // Constructors, getters, setters, etc.
}
