package com.example.shopAPI.auth.repository;

import com.example.shopAPI.auth.model.AuthUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<AuthUser, String> {
    Optional<AuthUser> findByEmail(String email);
}
