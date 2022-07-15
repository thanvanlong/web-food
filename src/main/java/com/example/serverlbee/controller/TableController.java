package com.example.serverlbee.controller;

import com.example.serverlbee.entity.Booking;
import com.example.serverlbee.entity.Error;
import com.example.serverlbee.entity.Table;
import com.example.serverlbee.service.book.BookServiceImpl;
import com.example.serverlbee.service.table.TableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TableController {

    @Autowired
    TableServiceImpl tableService;
    @Autowired
    BookServiceImpl bookService;

    @PostMapping("/table")
    public ResponseEntity<?> bookTable(@RequestBody Booking booking){
        List<Table> list = tableService.findTableByStatus(false);
        if(list.size() == 0){
            return ResponseEntity.ok(new Error(404, "Cơ sở tạm thời đã hết bàn rồi :(("));
        }
        Table table = tableService.updateTableStatus(list.get((int) (Math.random()*(list.size()-2))), true);
        booking.setTable(table);
        bookService.saveBook(booking);
        return ResponseEntity.status(404).body(new Error(404, "Cơ sở tạm thời đã hết bàn rồi :(("));
    }
}
