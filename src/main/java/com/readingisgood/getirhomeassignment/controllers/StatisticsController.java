package com.readingisgood.getirhomeassignment.controllers;

import com.readingisgood.getirhomeassignment.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/customers")
    public ResponseEntity<?> getStatsForAllCustomers() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getStatsForCustomerId(@PathVariable("id") Long customerId) {
        return ResponseEntity.noContent().build();
    }
}
