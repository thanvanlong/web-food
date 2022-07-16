package com.example.serverlbee.controller;

import com.example.serverlbee.entity.Bill;
import com.example.serverlbee.entity.Category;
import com.example.serverlbee.entity.Error;
import com.example.serverlbee.entity.Product;
import com.example.serverlbee.entity.User;
import com.example.serverlbee.service.bill.BillServiceImpl;
import com.example.serverlbee.service.cateogry.CategoryServiceImpl;
import com.example.serverlbee.service.product.ProductServiceImpl;
import com.example.serverlbee.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    UserServiceImpl userService;
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByPhone(principal.toString());
        System.out.println(user);
        bill.setUser(user);
        billService.saveBill(bill);
        return ResponseEntity.ok(bill);
    }

    @GetMapping("/product/bill")
    public ResponseEntity<?> getBillByUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Bill> bills = billService.getBillByUserPhone(principal.toString());
//        System.out.println(bills.size());
        if(bills.size() > 0){
            return ResponseEntity.ok(bills);
        }else{
            return ResponseEntity.status(404).body(new Error(404, "Chua co don hang nao :(("));
        }
    }


}
