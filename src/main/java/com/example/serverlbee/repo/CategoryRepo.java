package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepo extends MongoRepository<Category, String> {
    Optional<Category> findById(String id);
}
