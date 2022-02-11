package com.readingisgood.getirhomeassignment.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SqlResultSetMapping(
        name = "Statistics",
        classes = {
                @ConstructorResult(
                        targetClass = Statistics.class,
                        columns = {
                                @ColumnResult(name="CUSTOMER_ID", type = Long.class),
                                @ColumnResult(name="MONTH_NAME", type = String.class),
                                @ColumnResult(name="TOTAL_ORDER_COUNT", type = Integer.class),
                                @ColumnResult(name="TOTAL_BOOK_COUNT", type = Integer.class),
                                @ColumnResult(name="TOTAL_PURCHASED_AMOUNT", type = Double.class)
                        }
                )
        }
)
@NamedNativeQuery(
        name = "Order.getMonthlyStats",
        query =
                "SELECT CUSTOMER_ID, MONTHNAME(ORDER_DATE) as MONTH_NAME, COUNT(ID) as TOTAL_ORDER_COUNT, " +
                        "COUNT(bo.BOOK_ID) as TOTAL_BOOK_COUNT, SUM(AMOUNT) as TOTAL_PURCHASED_AMOUNT " +
                "FROM ORDERS o JOIN BOOKS_ORDERED bo on o.ID=bo.ORDER_ID " +
                "GROUP BY CUSTOMER_ID, MONTH(ORDER_DATE)",
        resultSetMapping = "Statistics"
)
@NamedNativeQuery(
        name = "Order.getMonthlyStatsForCustomerId",
        query =
                "SELECT CUSTOMER_ID, MONTHNAME(ORDER_DATE) as MONTH_NAME, COUNT(ID) as TOTAL_ORDER_COUNT, " +
                        "COUNT(bo.BOOK_ID) as TOTAL_BOOK_COUNT, SUM(AMOUNT) as TOTAL_PURCHASED_AMOUNT " +
                "FROM ORDERS o JOIN BOOKS_ORDERED bo on o.ID=bo.ORDER_ID " +
                "WHERE CUSTOMER_ID=:customerId " +
                "GROUP BY MONTH(ORDER_DATE)",
        resultSetMapping = "Statistics"
)
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = 0L;
    private Timestamp orderDate = new Timestamp(System.currentTimeMillis());
    private Integer quantityOrdered;
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
   //@JsonBackReference
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "books_ordered",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<Book>();

    public void addBooks(Book book) {
        this.books.add(book);
    }

    public void assignCustomer(Customer customer) {
        this.customer = customer;
    }

}
