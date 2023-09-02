package com.readingisgood.repositories;

import com.readingisgood.enities.Customer;
import com.readingisgood.enities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT o from #{#entityName} c JOIN c.orders o where c.id=:customerId")
    public Page<Order> findOrders(@Param("customerId") Long customerId, Pageable pageRequest);

}
