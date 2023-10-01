package com.readingisgood.daos.resources;

import java.util.List;

public record OrderRequest(
        Long customer_id,
        List<BooksQuantity> books_quantity,
        Double amount
) {
}
