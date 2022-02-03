package com.readingisgood.getirhomeassignment.repositories;

import com.readingisgood.getirhomeassignment.enities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
