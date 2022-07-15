package com.example.serverlbee.repo;

import com.example.serverlbee.entity.Table;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TableRepo extends MongoRepository<Table, String> {
    List<Table> findByTableStatus(Boolean tableStatus);
    Table findByTableName(String name);
}
