package com.readingisgood.repositories;

import com.readingisgood.enities.Order;
import com.readingisgood.enities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true)
    public Set<Statistics> getMonthlyStats();


    @Query(nativeQuery = true)
    public Set<Statistics> getMonthlyStatsForCustomerId(@Param("customerId") Long customerId);


    public Set<Order> findOrdersByOrderDateBetween(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
