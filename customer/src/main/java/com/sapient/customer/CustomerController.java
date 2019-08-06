package com.sapient.customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    @GetMapping("/customer")
    public Map<String, Object> get() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("Name", "Sagar Mal Shankhala");
        map.put("balance", 1000);
        map.put("dob", new Date());
        return map;
    }
}
