package com.readingisgood.getirhomeassignment.services;

import com.readingisgood.getirhomeassignment.enities.Customer;
import com.readingisgood.getirhomeassignment.enities.Order;
import com.readingisgood.getirhomeassignment.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findCustomerById(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        return customer.orElseThrow(NoSuchElementException::new);
    }

    public Set<Order> findOrdersForCustomerId(Long customerId) {

        return customerRepository.findOrders(customerId);
    }
}
