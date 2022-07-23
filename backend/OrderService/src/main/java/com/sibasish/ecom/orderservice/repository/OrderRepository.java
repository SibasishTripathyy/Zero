package com.sibasish.ecom.orderservice.repository;

import com.sibasish.ecom.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Modifying(clearAutomatically = true)
    @Query(
            value = "update productdb.product set units_left = units_left - :quantity " +
                    "where product_id = :productId",
            nativeQuery = true
    )
    Integer updateProductQuantity(
            @Param("productId") String productId,
            @Param("quantity") Integer quantity);
}
