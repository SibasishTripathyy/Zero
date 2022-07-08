package com.sibasish.ecom.customerservice.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private String email;
    private String password;
    private String mobile;

    @CreationTimestamp
    private Timestamp created_at;

    @UpdateTimestamp
    private Timestamp modified_at;

    @OneToOne
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_customer_role_role_id"))
    private Role role;
}
