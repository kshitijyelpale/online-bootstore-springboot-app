package com.readingisgood.getirhomeassignment.controllers;

import com.readingisgood.getirhomeassignment.enities.Customer;
import com.readingisgood.getirhomeassignment.enities.Order;
import com.readingisgood.getirhomeassignment.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
        log.info("Customer " + customer.getId() + " passed");
        Customer savedCustomer = customerService.saveCustomer(customer);
        EntityModel<Customer> resource = EntityModel.of(savedCustomer);

        return buildResponseEntity(savedCustomer.getId(), resource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable("id") Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        EntityModel<Customer> resource = EntityModel.of(customer);

        return buildResponseEntity(customer.getId(), resource, HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> findOrderByCustomerId(@PathVariable("id") Long customerId, @RequestParam Optional<Integer> page) {
        CollectionModel<Order> resource = CollectionModel.of(customerService.findOrdersForCustomerId(customerId, page));

        return buildResponseEntity(customerId, resource, HttpStatus.OK);
    }

    private ResponseEntity<?> buildResponseEntity(Long customerId, RepresentationModel representationModel, HttpStatus httpStatus) {
        representationModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class)
                .findCustomerById(customerId)).withSelfRel());
        representationModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class)
                .findOrderByCustomerId(customerId, Optional.empty())).withRel("orders"));

        return new ResponseEntity<>(representationModel, httpStatus);
    }
}
