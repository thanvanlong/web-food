package com.example.serverlbee.service.table;

import com.example.serverlbee.entity.Table;
import com.example.serverlbee.repo.TableRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService{
    @Autowired
    TableRepo tableRepo;
    @Override
    public List<Table> findTableByStatus(Boolean status) {
        return tableRepo.findByTableStatus(status);
    }

    @Override
    public Table updateTableStatus(Table table, Boolean status) {
        table.setTableStatus(status);
        tableRepo.save(table);
        return table;
    }


}
