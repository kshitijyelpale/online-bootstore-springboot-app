package com.readingisgood.daos.resources;

public record Statistics(
        Long customerId,
        String monthName,
        Integer totalOrderCount,
        Integer totalBookCount,
        Double totalPurchasedAmount
) {
}
