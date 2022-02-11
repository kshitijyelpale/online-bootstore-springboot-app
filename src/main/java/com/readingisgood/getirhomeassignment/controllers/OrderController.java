package com.readingisgood.getirhomeassignment.controllers;

import com.readingisgood.getirhomeassignment.enities.Book;
import com.readingisgood.getirhomeassignment.enities.Customer;
import com.readingisgood.getirhomeassignment.enities.Order;
import com.readingisgood.getirhomeassignment.enities.OrderRequest;
import com.readingisgood.getirhomeassignment.services.BookService;
import com.readingisgood.getirhomeassignment.services.CustomerService;
import com.readingisgood.getirhomeassignment.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.saveOrder(orderRequest);

        EntityModel<Order> resource = EntityModel.of(order);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .findOrderById(order.getId())).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable("id") Long orderId) {
        Order order = orderService.findOrderById(orderId);
        EntityModel<Order> resource = EntityModel.of(order);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .findOrderById(order.getId())).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/interval")
    public ResponseEntity<?> findOrderBetweenDates(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate
    ) {
        CollectionModel<Order> resource = CollectionModel.of(orderService.findOrdersBetweenDates(fromDate, toDate));

        return ResponseEntity.ok(resource);
    }
}
