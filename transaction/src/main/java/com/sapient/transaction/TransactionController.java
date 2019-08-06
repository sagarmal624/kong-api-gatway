package com.sapient.transaction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @GetMapping("/transaction")
    public Map<String, Object> get() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("amount", 500);
        map.put("discount", 100);
        map.put("customer", "Sagar");
        map.put("customer_id", 1);
        map.put("date", new Date());
        return map;
    }
}
