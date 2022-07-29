package com.sibasish.ecom.cartservice.repository;

import com.sibasish.ecom.cartservice.entity.Cart;
import com.sibasish.ecom.cartservice.response.ProductAndQuantityForCustomerResponse;
import com.sibasish.ecom.cartservice.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "select product_id as productId, name as productName, description, " +
            "price, units_left as unitsLeft, out_of_stock as outOfStock " +
            "from productdb.product where product_id = :productId",
            nativeQuery = true)
    ProductResponse findProductById(@Param("productId") String productId);

    @Query(value = "select product_id as productId, quantity from cartdb.cart where customer_id=:customerId",
            nativeQuery = true)
    List<ProductAndQuantityForCustomerResponse> findProductAndQuantityForCustomer(@Param("customerId") String customerId);

    void deleteByCustomerId(UUID id);
}
