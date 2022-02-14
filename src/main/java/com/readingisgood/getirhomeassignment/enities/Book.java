package com.readingisgood.getirhomeassignment.enities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book")
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id = 0L;

    @Getter
    private String name;

    @Getter
    private String publisher;

    @Getter
    private Integer stock;

    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private Set<Order> orders = new HashSet<>();

    @JsonBackReference
    public Set<Order> getOrders() {
        return orders;
    }
}
