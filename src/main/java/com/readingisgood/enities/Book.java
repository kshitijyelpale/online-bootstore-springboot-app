package com.readingisgood.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
    private Long id = 0L;

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
    private Set<Order> orders = new HashSet<>();

    @JsonBackReference
    public Set<Order> getOrders() {
        return orders;
    }
}
