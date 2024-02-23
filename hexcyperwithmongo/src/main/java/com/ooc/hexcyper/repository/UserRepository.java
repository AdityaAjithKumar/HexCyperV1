package com.ooc.hexcyper.repository;

import com.ooc.hexcyper.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByGoogleId(String googleId);
}
