package com.readingisgood.getirhomeassignment.enities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooksQuantity {
    private Long bookId;
    private Integer quantity;
}
