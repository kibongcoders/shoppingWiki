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
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    @Comment("카테고리 아이디")
    private Long id;

    @Column(name="category_name",nullable = false)
    @Comment("카테고리 이름")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    @Comment("카테고리 상품 리스트")
    List<CategoryProduct> categoryProductList = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Comment("카테고리 리스트")
    List<Content> contentList = new ArrayList<>();

    public Category() {
    }

    @Builder
    public Category(String categoryName, List<CategoryProduct> categoryProductList, List<Content> contentList) {
        this.categoryName = categoryName;
        this.categoryProductList = categoryProductList;
        this.contentList = contentList;
    }
}
