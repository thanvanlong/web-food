package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepo extends MongoRepository<Role, Long> {
    Role findByName(String name);
}
