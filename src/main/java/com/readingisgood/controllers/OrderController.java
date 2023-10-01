package com.readingisgood.controllers;

import com.readingisgood.daos.enities.Order;
import com.readingisgood.daos.resources.OrderRequest;
import com.readingisgood.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return buildResponseEntity(order.getId(), resource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable("id") Long orderId) {
        Order order = orderService.findOrderById(orderId);
        EntityModel<Order> resource = EntityModel.of(order);

        return buildResponseEntity(order.getId(), resource, HttpStatus.OK);
    }

    @GetMapping("/interval")
    public ResponseEntity<?> findOrderBetweenDates(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate
    ) {
        CollectionModel<Order> resource = CollectionModel.of(orderService.findOrdersBetweenDates(fromDate, toDate));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .findOrderBetweenDates(fromDate, toDate)).withRel("interval"));

        return buildResponseEntity(0L, resource, HttpStatus.OK);
    }

    private ResponseEntity<?> buildResponseEntity(Long orderId, RepresentationModel representationModel, HttpStatus httpStatus) {
        representationModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .findOrderById(orderId)).withSelfRel());

        return new ResponseEntity<>(representationModel, httpStatus);
    }
}
