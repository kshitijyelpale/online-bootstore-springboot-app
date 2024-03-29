package com.readingisgood.services;

import com.readingisgood.daos.enities.*;
import com.readingisgood.daos.resources.BooksQuantity;
import com.readingisgood.daos.resources.OrderRequest;
import com.readingisgood.daos.resources.Statistics;
import com.readingisgood.exception.ServiceException;
import com.readingisgood.repositories.BooksOrderedRepository;
import com.readingisgood.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final BooksOrderedRepository booksOrderedRepository;

    private final CustomerService customerService;

    private final BookService bookService;

    OrderService(
            OrderRepository orderRepository,
            BooksOrderedRepository booksOrderedRepository,
            CustomerService customerService,
            BookService bookService
    ) {
        this.orderRepository = orderRepository;
        this.booksOrderedRepository = booksOrderedRepository;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Transactional
    public Order saveOrder(OrderRequest orderRequest) {
        Long customerId = orderRequest.customer_id();

        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            throw new ServiceException("Customer does not exist.");
        }

        List<BooksQuantity> booksWithQuantity = orderRequest.books_quantity();

        if (booksWithQuantity.isEmpty()) throw new ServiceException("No books are in order request.");

        Map<Long, Integer> booksQuant = new HashMap<>();
        Set<Book> books = new HashSet<>();
        synchronized (booksWithQuantity) {
            booksWithQuantity.forEach(booksQuantity -> {
                Book book = bookService.findBookById(booksQuantity.bookId());
                if (book.getStock() == 0) {
                    throw new ServiceException(String.format("Book %s is out of stock.", book.getName()));
                }

                if (book.getStock() - booksQuantity.quantity() < 0) {
                    throw new ServiceException(String.format("Book %s is not available in requested quantity.", book.getName()));
                }

                bookService.updateQuantityForBook(book.getId(), book.getStock() - booksQuantity.quantity());
                booksQuant.put(book.getId(), booksQuantity.quantity());
                books.add(book);
            });
        }
        if (books.isEmpty()) {
            throw new ServiceException("Books do not exist.");
        }

        Order order = new Order();

        order.setCustomer(customer);
        order.setAmount(orderRequest.amount());
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
