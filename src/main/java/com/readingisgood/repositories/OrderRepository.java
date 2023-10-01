package com.readingisgood.repositories;

import com.readingisgood.daos.enities.Order;
import com.readingisgood.daos.resources.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true)
    public Set<Statistics> getMonthlyStats();


    @Query(nativeQuery = true)
    public Set<Statistics> getMonthlyStatsForCustomerId(@Param("customerId") Long customerId);


    public Set<Order> findOrdersByOrderDateBetween(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
