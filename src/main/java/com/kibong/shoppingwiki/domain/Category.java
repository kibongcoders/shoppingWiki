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
public class Category extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    @Comment("카테고리 아이디")
    private Long id;

    @Column(name = "category_name", nullable = false)
    @Comment("카테고리 이름")
    private String categoryName;

    @Column(name = "parent_id")
    @Comment("카테고리 부모 아이디")
    private Long parentId;

    @OneToMany(mappedBy = "category")
    List<ContentsCategory> contentsCategoryList = new ArrayList<>();

    public Category() {
    }

    @Builder
    public Category(String categoryName, Long parentId, List<ContentsCategory> contentsCategoryList) {
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.contentsCategoryList = contentsCategoryList;
    }
}
