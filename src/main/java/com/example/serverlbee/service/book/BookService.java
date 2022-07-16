package com.example.serverlbee.service.book;

import com.example.serverlbee.entity.Booking;
import com.example.serverlbee.entity.User;

import java.util.List;

public interface BookService {
    void saveBook(Booking booking);
    List<Booking> getBookByUserPhone(String phone);
}
