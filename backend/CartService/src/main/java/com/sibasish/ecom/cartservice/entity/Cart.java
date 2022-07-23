package com.sibasish.ecom.cartservice.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;

    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID customerId;

    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID productId;
    private Integer quantity;
}
