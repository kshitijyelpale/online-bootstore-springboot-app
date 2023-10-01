package com.readingisgood.services;

import com.readingisgood.enities.Customer;
import com.readingisgood.enities.Order;
import com.readingisgood.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findCustomerById(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        return customer.orElseThrow(NoSuchElementException::new);
    }

    public Page<Order> findOrdersForCustomerId(Long customerId, Optional<Integer> page) {

        return customerRepository.findOrders(customerId, PageRequest.of(page.orElse(0), 1));
    }
}
