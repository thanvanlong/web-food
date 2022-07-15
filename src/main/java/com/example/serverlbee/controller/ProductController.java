package com.example.serverlbee.controller;

import com.example.serverlbee.entity.Bill;
import com.example.serverlbee.entity.Category;
import com.example.serverlbee.entity.Product;
import com.example.serverlbee.service.bill.BillServiceImpl;
import com.example.serverlbee.service.cateogry.CategoryServiceImpl;
import com.example.serverlbee.service.product.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    BillServiceImpl billService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/product/category")
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok(categoryService.getAll());
    }

    @PostMapping("/product/order")
    public ResponseEntity<?> orderFood(@RequestBody Bill bill){
        billService.saveBill(bill);
        return ResponseEntity.ok(bill);
    }


}
