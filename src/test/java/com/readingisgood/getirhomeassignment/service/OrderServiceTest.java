package com.readingisgood.getirhomeassignment.service;

import com.readingisgood.getirhomeassignment.enities.*;
import com.readingisgood.getirhomeassignment.exception.CustomException;
import com.readingisgood.getirhomeassignment.repositories.CustomerRepository;
import com.readingisgood.getirhomeassignment.services.BookService;
import com.readingisgood.getirhomeassignment.services.CustomerService;
import com.readingisgood.getirhomeassignment.services.OrderService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CustomerService customerService;

    @Mock
    private BookService bookService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Customer customer = new Customer(2L, "Some name", "Some number", "Some email", Set.of());

    private Book book = new Book(3L, "Java", "O'reily", 3, Set.of());
    private Book book2 = new Book(4L, "Javascript", "O'reily", 0, Set.of());

    private OrderRequest orderRequest = new OrderRequest(
            2L,
            new ArrayList<>(List.of(
                    new BooksQuantity(3L, 4)
            )),
            100.0
    );

    private OrderRequest orderRequest2 = new OrderRequest(
            2L,
            new ArrayList<>(List.of(
                    new BooksQuantity(4L, 4)
            )),
            100.0
    );

    private OrderRequest orderRequestWithNoBooks = new OrderRequest(
            2L,
            new ArrayList<>(),
            100.0
    );


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void customerDoesNotExist() {
        expectedException.expect(CustomException.class);
        expectedException.expectMessage("Customer does not exist.");

        when(customerService.findCustomerById(anyLong())).thenReturn(null);

        orderService.saveOrder(orderRequest);
    }

    @Test
    public void noBooksInRequest() {
        expectedException.expect(CustomException.class);
        expectedException.expectMessage("No books are in order request.");

        when(customerService.findCustomerById(orderRequest.getCustomer_id())).thenReturn(customer);

        orderService.saveOrder(orderRequestWithNoBooks);
    }

    @Test
    public void requestQuantityNoAvailable() {
        expectedException.expect(CustomException.class);
        expectedException.expectMessage(String.format("Book %s is not available in requested quantity.", book.getName()));

        when(customerService.findCustomerById(orderRequest.getCustomer_id())).thenReturn(customer);
        when(bookService.findBookById(anyLong())).thenReturn(book);

        orderService.saveOrder(orderRequest);
    }

    @Test
    public void booksOutOfStock() {
        expectedException.expect(CustomException.class);
        expectedException.expectMessage(String.format("Book %s is out of stock.", book2.getName()));

        when(customerService.findCustomerById(orderRequest.getCustomer_id())).thenReturn(customer);
        when(bookService.findBookById(anyLong())).thenReturn(book2);

        orderService.saveOrder(orderRequest2);
    }

}
