package com.readingisgood.getirhomeassignment.controllers;

import com.readingisgood.getirhomeassignment.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


}
