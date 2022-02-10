package com.readingisgood.getirhomeassignment.controllers;

import com.readingisgood.getirhomeassignment.enities.Book;
import com.readingisgood.getirhomeassignment.enities.Customer;
import com.readingisgood.getirhomeassignment.enities.Order;
import com.readingisgood.getirhomeassignment.enities.OrderRequest;
import com.readingisgood.getirhomeassignment.services.BookService;
import com.readingisgood.getirhomeassignment.services.CustomerService;
import com.readingisgood.getirhomeassignment.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderRequest orderRequest) {

        Long customerId = orderRequest.getCustomer_id();

        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) return ResponseEntity.noContent().build();

        Set<Long> bookIds = orderRequest.getBook_ids();
        Set<Book> books = new HashSet<>();
        bookIds.forEach(bookId -> {
            Book book = bookService.findBookById(bookId);
            if (book != null) {
                books.add(book);
            }
        });
        if (books.isEmpty()) return ResponseEntity.noContent().build();

        Order order = new Order();

        order.setCustomer(customer);
        order.setAmount(orderRequest.getAmount());
        order.setBooks(books);

        Order savedOrder = orderService.saveOrder(order);
        EntityModel<Order> resource = EntityModel.of(savedOrder);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .findOrderById(savedOrder.getId())).withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable("id") Long orderId) {
        Order order = orderService.findOrderById(orderId);
        EntityModel<Order> resource = EntityModel.of(order);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderController.class)
                .findOrderById(order.getId())).withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping("/interval?start")
    public ResponseEntity<?> findOrderBetweenDates() {
        return ResponseEntity.noContent().build();
    }
}
