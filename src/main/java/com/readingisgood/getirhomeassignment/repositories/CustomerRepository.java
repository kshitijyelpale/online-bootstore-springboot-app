package com.readingisgood.getirhomeassignment.repositories;

import com.readingisgood.getirhomeassignment.enities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
