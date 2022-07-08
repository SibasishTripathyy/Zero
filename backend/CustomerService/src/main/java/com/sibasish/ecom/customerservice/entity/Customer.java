package com.sibasish.ecom.customerservice.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID customerId;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;
    private String mobile;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime modified_at;

    @OneToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_customer_role_role_id"))
    private Role role;

    @OneToMany
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_customer_address_customer_customer_id")
    )
    private List<CustomerAddress> customerAddressList;
}
