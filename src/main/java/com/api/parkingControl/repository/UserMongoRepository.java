package com.api.parkingControl.repository;

import com.api.parkingControl.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<User, String> {
    User findByName(String name);
}
