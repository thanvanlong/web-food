package com.example.serverlbee.service.book;

import com.example.serverlbee.entity.Booking;
import com.example.serverlbee.entity.User;
import com.example.serverlbee.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepo bookRepo;

    @Override
    public void saveBook(Booking booking) {

        bookRepo.save(booking);

    }

    @Override
    public List<Booking> getBookByUserPhone(String phone) {

        return bookRepo.findByUser_Phone(phone);
    }
}
