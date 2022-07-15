package com.example.serverlbee.service.product;

import com.example.serverlbee.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    int updateProduct(Product product);

}
