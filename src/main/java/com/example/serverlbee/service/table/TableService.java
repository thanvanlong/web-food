package com.example.serverlbee.service.table;

import com.example.serverlbee.entity.Table;

import java.util.List;

public interface TableService {
    List<Table> findTableByStatus(Boolean status);
    Table updateTableStatus(Table table, Boolean status);
}
