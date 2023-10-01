package com.readingisgood.enities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.jetbrains.annotations.NotNull;

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
    private Long id;

    @Getter
    @NotNull(value = "Customer name should not be null")
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
    private Set<Order> orders;

    @JsonBackReference
    public Set<Order> getOrders() {
        return orders;
    }
}
