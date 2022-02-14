package com.readingisgood.getirhomeassignment.services;

import com.readingisgood.getirhomeassignment.enities.*;
import com.readingisgood.getirhomeassignment.exception.CustomException;
import com.readingisgood.getirhomeassignment.repositories.BooksOrderedRepository;
import com.readingisgood.getirhomeassignment.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BooksOrderedRepository booksOrderedRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BookService bookService;
    private Object BooksOrders;

    @Transactional
    public Order saveOrder(OrderRequest orderRequest) {
        Long customerId = orderRequest.getCustomer_id();

        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            throw new CustomException("Customer does not exist.");
        }

        List<BooksQuantity> booksWithQuantity = orderRequest.getBooks_quantity();

        if (booksWithQuantity.isEmpty()) throw new CustomException("No books are in order request.");

        Map<Long, Integer> booksQuant = new HashMap<>();
        Set<Book> books = new HashSet<>();
        booksWithQuantity.forEach(booksQuantity -> {
            Book book = bookService.findBookById(booksQuantity.getBookId());
            if (book.getStock() == 0) {
                throw new CustomException(String.format("Book %s is out of stock.", book.getName()));
            }

            if (book.getStock() - booksQuantity.getQuantity() < 0) {
                throw new CustomException(String.format("Book %s is not available in requested quantity.", book.getName()));
            }

            bookService.updateQuantityForBook(book.getId(), book.getStock() - booksQuantity.getQuantity());
            booksQuant.put(book.getId(), booksQuantity.getQuantity());
            books.add(book);
        });
        if (books.isEmpty()) {
            throw new CustomException("Books do not exist.");
        }

        Order order = new Order();

        order.setCustomer(customer);
        order.setAmount(orderRequest.getAmount());
        order.setBooks(books);

        Order savedOrder = orderRepository.save(order);

        books.forEach(book -> {
                var booksOrders = booksOrderedRepository.findByOrderIdAndBookId(savedOrder.getId(), book.getId());
                if (booksOrders.isPresent()) {
                    booksOrders.get().setQuantity(booksQuant.get(book.getId()));
                    booksOrderedRepository.save(booksOrders.get());
                }
            }
        );

        return savedOrder;
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
