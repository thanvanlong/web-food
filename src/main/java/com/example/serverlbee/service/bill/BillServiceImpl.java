package com.example.serverlbee.service.bill;

import com.example.serverlbee.entity.Bill;
import com.example.serverlbee.repo.BillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService{

    @Autowired
    BillRepo billRepo;

    @Override
    public int saveBill(Bill bill) {
        billRepo.save(bill);
        return 1;
    }

    @Override
    public int updateBill(String id) {
        return 0;
    }
}
