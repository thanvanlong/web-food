package com.example.serverlbee.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Booking {
    private String id;
    private String booker;
    private String note;
    private int people;
    private String phone;
    private LocalDateTime date;
    private Table table;

}