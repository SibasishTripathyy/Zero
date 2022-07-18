package com.sibasish.ecom.productservice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;
    private String itemName;
    private Double itemPrice;
    private Integer quantity;
    private LocalDateTime orderTimestamp; // ToDo: Check if it needs to be there

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime modified_at;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @OneToOne
    @JoinTable(
            name = "ordered_product",
            joinColumns = @JoinColumn(name = "order_item_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Product product;

}