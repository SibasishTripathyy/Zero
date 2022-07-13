package com.sibasish.ecom.orderservice.repository;

import com.sibasish.ecom.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
