package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String category_name;

    @OneToMany(mappedBy = "category")
    List<CategoryProduct> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    List<Content> contentList = new ArrayList<>();

    public Category() {
    }

    @Builder
    public Category(String category_name, List<CategoryProduct> categoryList, List<Content> contentList) {
        this.category_name = category_name;
        this.categoryList = categoryList;
        this.contentList = contentList;
    }
}
