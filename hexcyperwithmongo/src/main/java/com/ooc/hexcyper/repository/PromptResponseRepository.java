package com.ooc.hexcyper.repository;

import com.ooc.hexcyper.model.PromptResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PromptResponseRepository extends MongoRepository<PromptResponse, String> {
    List<PromptResponse> findByUserId(String userId);
}
