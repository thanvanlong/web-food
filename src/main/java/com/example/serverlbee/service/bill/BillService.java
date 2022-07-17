package com.example.serverlbee.service.bill;

import com.example.serverlbee.entity.Bill;
import com.example.serverlbee.entity.User;

import java.util.List;

public interface BillService {
    int saveBill(Bill bill);
    int updateBill(String id);

    List<Bill> getBillByUserPhone(String userPhone);

    List<Bill> getBillByStatus(String status);
}
