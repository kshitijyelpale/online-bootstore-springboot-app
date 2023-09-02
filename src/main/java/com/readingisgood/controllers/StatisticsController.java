package com.readingisgood.controllers;

import com.readingisgood.enities.Statistics;
import com.readingisgood.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
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
        CollectionModel<Statistics> resource = CollectionModel.of(orderService.findAllMonthlyStatsForAllCustomers());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getStatsForCustomerId(@PathVariable("id") Long customerId) {
        CollectionModel<Statistics> resource = CollectionModel.of(orderService.findMonthlyStatsByCustomerId(customerId));
        return ResponseEntity.ok(resource);
    }
}
