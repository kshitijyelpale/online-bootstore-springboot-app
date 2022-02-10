package com.readingisgood.getirhomeassignment.repositories;

import com.readingisgood.getirhomeassignment.enities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT month(ORDER_DATE) as MONTH, COUNT(ID) as TOTAL_ORDER_COUNT, COUNT(bo.BOOK_ID) as TOTAL_BOOK_COUNT, SUM(AMOUNT) as TOTAL_PURCHASED_AMOUNT\n" +
            "FROM ORDERS o JOIN BOOKS_ORDERED bo on o.ID=bo.ORDER_ID\n" +
            "GROUP BY CUSTOMER_ID, month(ORDER_DATE)", nativeQuery = true)
    public List<Order> getMonthlyStats();


    @Query(value = "SELECT month(ORDER_DATE) as MONTH, COUNT(ID) as TOTAL_ORDER_COUNT, COUNT(bo.BOOK_ID) as TOTAL_BOOK_COUNT, SUM(AMOUNT) as TOTAL_PURCHASED_AMOUNT\n" +
            "FROM ORDERS o JOIN BOOKS_ORDERED bo on o.ID=bo.ORDER_ID\n" +
            "WHERE CUSTOMER_ID=:customerId" +
            "GROUP BY month(ORDER_DATE)", nativeQuery = true)
    public List<Order> getMonthlyStatsForCustomerId(Long customerId);

    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    public Set<Order> findOrdersByOrderDateBetween(Timestamp startDate, Timestamp endDate);
}
