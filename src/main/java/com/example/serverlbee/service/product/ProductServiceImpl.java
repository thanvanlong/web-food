package com.example.serverlbee.service.product;

import com.example.serverlbee.entity.Product;
import com.example.serverlbee.repo.ProductRepo;
import com.example.serverlbee.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;
    @Override
    public List<Product> getAll() {
       return productRepo.findAll();
    }

    @Override
    public int updateProduct(Product product) {
        return 0;
    }
}
