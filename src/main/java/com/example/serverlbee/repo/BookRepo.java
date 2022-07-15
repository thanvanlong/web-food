package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepo extends MongoRepository<Booking, String> {

}
