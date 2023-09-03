package com.readingisgood.enities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.HashSet;
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
                "SELECT CUSTOMER_ID, MONTHNAME(ORDER_DATE) as MONTH_NAME, COUNT(DISTINCT(ID)) as TOTAL_ORDER_COUNT, " +
                        "SUM(d.QUANTITY) as TOTAL_BOOK_COUNT, SUM(AMOUNT) as TOTAL_PURCHASED_AMOUNT " +
                "FROM ORDERS o INNER JOIN ( " +
                        "SELECT ORDER_ID, SUM(QUANTITY) as QUANTITY " +
                        "FROM BOOKS_ORDERED " +
                        "GROUP BY ORDER_ID " +
                    ") d on o.ID=d.ORDER_ID " +
                "GROUP BY CUSTOMER_ID, MONTHNAME(ORDER_DATE)",
        resultSetMapping = "Statistics"
)
@NamedNativeQuery(
        name = "Order.getMonthlyStatsForCustomerId",
        query =
                "SELECT CUSTOMER_ID, MONTHNAME(ORDER_DATE) as MONTH_NAME, COUNT(DISTINCT(ID)) as TOTAL_ORDER_COUNT, " +
                        "SUM(d.QUANTITY) as TOTAL_BOOK_COUNT, SUM(AMOUNT) as TOTAL_PURCHASED_AMOUNT " +
                "FROM ORDERS o INNER JOIN (" +
                        "SELECT ORDER_ID, SUM(QUANTITY) as QUANTITY " +
                        "FROM BOOKS_ORDERED " +
                        "GROUP BY ORDER_ID" +
                    ") d on o.ID=d.ORDER_ID " +
                "WHERE CUSTOMER_ID=:customerId " +
                "GROUP BY MONTHNAME(ORDER_DATE)",
        resultSetMapping = "Statistics"
)
@Entity
@Table(name = "orders")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id = 0L;

    @Getter
    private Timestamp orderDate = new Timestamp(System.currentTimeMillis());

    @Getter
    @Min(value = 0, message = "Amount cannot be less than 0")
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "books_ordered",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<Book>();

    @JsonManagedReference
    public Customer getCustomer() {
        return customer;
    }

    @JsonManagedReference
    public Set<Book> getBooks() {
        return books;
    }

    public void addBooks(Book book) {
        this.books.add(book);
    }

    public void assignCustomer(Customer customer) {
        this.customer = customer;
    }

}
