package com.readingisgood.repository;

import com.readingisgood.enities.Customer;
import com.readingisgood.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveCustomerTest() {
        Customer customer = Customer.builder()
                .name("Ramesh")
                .contactNumber("+49 781512122")
                .email("anc@gmail.com")
                .build();

        customerRepository.save(customer);

        assertThat(customer.getId()).isGreaterThan(0);
    }

    @Test
    public void getCustomerTest() {
        Customer customer = customerRepository.findById(1L).get();

        assertThat(customer.getId()).isEqualTo(1);
    }

    @Test
    public void getListOfCustomersTest() {
        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.size()).isGreaterThan(0);
    }
}
