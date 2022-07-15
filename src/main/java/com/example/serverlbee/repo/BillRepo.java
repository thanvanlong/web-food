package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BillRepo extends MongoRepository<Bill, String> {
}
