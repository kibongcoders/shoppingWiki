package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_product_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public CategoryProduct() {
    }

    public CategoryProduct(Long id, Category category, Product product) {
        this.id = id;
        this.category = category;
        this.product = product;
    }
}
