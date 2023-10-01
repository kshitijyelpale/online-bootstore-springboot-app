package com.readingisgood.enities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksOrdersId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "book_id")
    private Long bookId;
}
