package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DishRepo extends MongoRepository<Dish, String> {
}
