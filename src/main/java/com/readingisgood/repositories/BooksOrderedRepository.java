package com.readingisgood.repositories;

import com.readingisgood.enities.BooksOrders;
import com.readingisgood.enities.BooksOrdersId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BooksOrderedRepository extends JpaRepository<BooksOrders, BooksOrdersId> {

    public Optional<BooksOrders> findByOrderIdAndBookId(Long orderId, Long bookId);
}
