package com.example.serverlbee.controller;

import com.example.serverlbee.entity.*;
import com.example.serverlbee.entity.Error;
import com.example.serverlbee.service.book.BookServiceImpl;
import com.example.serverlbee.service.table.TableServiceImpl;
import com.example.serverlbee.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    UserServiceImpl userService;
    @PostMapping("/table")
    public ResponseEntity<?> bookTable(@RequestBody Booking booking){
        List<Table> list = tableService.findTableByStatus(false);
        if(list.size() == 0){
            return ResponseEntity.ok(new Error(404, "Cơ sở tạm thời đã hết bàn rồi :(("));
        }
        Table table = tableService.updateTableStatus(list.get((int) (Math.random()*(list.size()-2))), true);
        booking.setTable(table);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUserByPhone(principal.toString());
        booking.setUser(user);
//        System.out.println(booking.getUser());
        bookService.saveBook(booking);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/table/book")
    public ResponseEntity<?> getTableBooked(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Booking> bookingList = bookService.getBookByUserPhone(principal.toString());
        System.out.println(bookingList.size());
        if(bookingList.size() > 0){
            return ResponseEntity.ok(bookingList);
        }else {
            return ResponseEntity.status(404).body(new Error(404, "Khong co don nao :(("));
        }
    }

}
