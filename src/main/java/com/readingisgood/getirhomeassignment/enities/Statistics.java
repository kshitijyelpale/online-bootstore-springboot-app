package com.readingisgood.getirhomeassignment.enities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    private Long customerId;
    private String monthName;
    private Integer totalOrderCount;
    private Integer totalBookCount;
    private Double totalPurchasedAmount;

}
