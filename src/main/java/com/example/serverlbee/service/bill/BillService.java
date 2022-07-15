package com.example.serverlbee.service.bill;

import com.example.serverlbee.entity.Bill;

public interface BillService {
    int saveBill(Bill bill);
    int updateBill(String id);
}
