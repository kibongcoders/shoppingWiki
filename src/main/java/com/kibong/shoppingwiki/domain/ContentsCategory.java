package com.kibong.shoppingwiki.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table
public class ContentsCategory extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_category_id", nullable = false)
    @Comment("콘텐츠 카테고리 아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public ContentsCategory() {
    }

    @Builder
    public ContentsCategory(Contents contents, Category category) {
        this.contents = contents;
        this.category = category;
    }
}
