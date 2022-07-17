package com.sibasish.ecom.orderservice.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "categoryList")
    private List<Product> productList;
}
