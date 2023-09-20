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

    @Column(nullable = false)
    @Comment("카테고리 이름")
    private String category_name;

    @OneToMany(mappedBy = "category")
    @Comment("카테고리 상품 리스트")
    List<CategoryProduct> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Comment("카테고리 리스트")
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
