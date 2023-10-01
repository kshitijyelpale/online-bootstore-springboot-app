package com.readingisgood.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @NotNull(message = "Book name should not be null")
    private String name;

    @Getter
    @NotNull(message = "Publisher should not be null")
    private String publisher;

    @Getter
    @Min(value = 0, message = "Stock should be greater than or equal to 0")
//    @PositiveOrZero(message = "Stock should be greater than or equal to 0")
    private Integer stock;

    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private Set<Order> orders;

    @JsonBackReference
    public Set<Order> getOrders() {
        return orders;
    }
}
