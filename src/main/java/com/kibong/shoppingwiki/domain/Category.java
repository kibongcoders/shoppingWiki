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
    private String category_name;

    @Column(name = "parent_id")
    @Comment("카테고리 부모 아이디")
    private Long parent_id;

    @OneToMany(mappedBy = "category")
    List<ContentsCategory> contentsCategoryList = new ArrayList<>();

    public Category() {
    }

    @Builder
    public Category(String category_name, Long parent_id, List<ContentsCategory> contentsCategoryList) {
        this.category_name = category_name;
        this.parent_id = parent_id;
        this.contentsCategoryList = contentsCategoryList;
    }
}
