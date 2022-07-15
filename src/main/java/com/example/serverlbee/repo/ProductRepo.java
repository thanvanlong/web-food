package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String> {
}
