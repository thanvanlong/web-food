package com.example.serverlbee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "bill")
public class Bill {
    private String id;
    private String orderer;
    private String phone;
    private List<Product> productList;
    private List<Integer> quanityList;
    private String address;
    private String note;
    private String status;
    private LocalDateTime timeDelivery;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
