package com.readingisgood.getirhomeassignment.enities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books_ordered")
@Data
public class BooksOrders implements Serializable {

    @EmbeddedId
    private BooksOrdersId booksOrdersId;

    @ManyToOne
    @MapsId("orderId")
    private Order order;

    @ManyToOne
    @MapsId("bookId")
    private Book book;

    @Column
    private Integer quantity;
}
