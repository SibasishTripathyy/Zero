package com.sibasish.ecom.cartservice.repository;

import com.sibasish.ecom.cartservice.entity.Cart;
import com.sibasish.ecom.cartservice.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "select product_id as productId, name as productName, description, " +
            "price, units_left as unitsLeft, out_of_stock as outOfStock " +
            "from productdb.product where product_id = :productId",
            nativeQuery = true)
    ProductResponse findProductById(@Param("productId") String productId);
}
