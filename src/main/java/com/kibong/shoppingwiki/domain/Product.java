package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    @Comment("상품 아이디")
    private Long id;

    @Column(nullable = false)
    @Comment("상품 내용")
    private String product_name;

    @OneToMany(mappedBy = "product")
    @Comment("상품 리스트")
    List<CategoryProduct> productList = new ArrayList<>();

    public Product() {
    }

    @Builder
    public Product(String product_name, List<CategoryProduct> productList) {
        this.product_name = product_name;
        this.productList = productList;
    }
}
