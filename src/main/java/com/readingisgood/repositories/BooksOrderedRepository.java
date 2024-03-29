package com.readingisgood.repositories;

import com.readingisgood.daos.enities.BooksOrders;
import com.readingisgood.daos.enities.BooksOrdersId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BooksOrderedRepository extends JpaRepository<BooksOrders, BooksOrdersId> {

    public Optional<BooksOrders> findByOrderIdAndBookId(Long orderId, Long bookId);
}
