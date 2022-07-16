package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Booking;
import com.example.serverlbee.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepo extends MongoRepository<Booking, String> {
    List<Booking> findByUser(User user);
    List<Booking> findByUser_Phone(String phone);
}
