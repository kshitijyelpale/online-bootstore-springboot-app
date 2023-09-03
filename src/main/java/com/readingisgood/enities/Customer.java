package com.readingisgood.enities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id = 0L;

    @Getter
    @NotNull(message = "Customer name should not be null")
    private String name;

    @Getter
    @Size(min = 10, max = 14, message = "Contact number should be between 10 and 14 characters")
    private String contactNumber;

    @Column(unique = true)
    @Getter
    @Email(message = "Email should be valid")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    @JsonBackReference
    public Set<Order> getOrders() {
        return orders;
    }
}
