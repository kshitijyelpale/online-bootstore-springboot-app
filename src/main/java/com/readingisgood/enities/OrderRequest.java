package com.readingisgood.enities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private Long customer_id;
    private List<BooksQuantity> books_quantity;
    private Double amount;
}

