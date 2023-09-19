package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String product_name;

    @OneToMany(mappedBy = "product")
    List<CategoryProduct> productList = new ArrayList<>();

    public Product() {
    }

    @Builder
    public Product(String product_name, List<CategoryProduct> productList) {
        this.product_name = product_name;
        this.productList = productList;
    }
}
