package com.readingisgood.getirhomeassignment.enities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksOrdersId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "book_id")
    private Long bookId;
}
