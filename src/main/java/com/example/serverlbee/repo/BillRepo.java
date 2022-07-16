package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Bill;
import com.example.serverlbee.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BillRepo extends MongoRepository<Bill, String> {
    List<Bill> findByUser(User user);
    List<Bill> findByUser_Phone(String phonenumber);
}
