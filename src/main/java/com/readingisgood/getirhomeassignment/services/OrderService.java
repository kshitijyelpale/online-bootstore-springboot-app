package com.readingisgood.getirhomeassignment.services;

import com.readingisgood.getirhomeassignment.enities.*;
import com.readingisgood.getirhomeassignment.exception.CustomException;
import com.readingisgood.getirhomeassignment.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;

    @Transactional
    public Order saveOrder(OrderRequest orderRequest) {
        Long customerId = orderRequest.getCustomer_id();

        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            throw new CustomException("Customer does not exist.");
        }

        Set<Long> bookIds = orderRequest.getBook_ids();

        if (bookIds.isEmpty()) throw new CustomException("No books are in order request.");

        Set<Book> books = new HashSet<>();
        bookIds.forEach(bookId -> {
            Book book = bookService.findBookById(bookId);
            if (book.getStock() == 0) {
                throw new CustomException(String.format("Book %s is out of stock.", book.getName()));
            }
            books.add(book);
        });
        if (books.isEmpty()) {
            throw new CustomException("Books do not exist.");
        }

        Order order = new Order();

        order.setCustomer(customer);
        order.setAmount(orderRequest.getAmount());
        order.setBooks(books);

        return orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        return order.orElseThrow(NoSuchElementException::new);
    }

    public Set<Order> findOrdersBetweenDates(Date fromDate, Date toDate) {
        return orderRepository.findOrdersByOrderDateBetween(fromDate, toDate);
    }

    public Set<Statistics> findAllMonthlyStatsForAllCustomers() {
        return orderRepository.getMonthlyStats();
    }

    public Set<Statistics> findMonthlyStatsByCustomerId(Long customerId) {
        return orderRepository.getMonthlyStatsForCustomerId(customerId);
    }


}
