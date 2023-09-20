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
public class Content extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id", nullable = false)
    @Comment("내용 아이디")
    private Long id;

    @Column(nullable = false)
    @Comment("내용 제목")
    private String content_subject;

    @Column(columnDefinition = "LONGTEXT")
    @Comment("내용 상세")
    private String content_detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Comment("카테고리 아이디")
    Category category;

    @OneToMany(mappedBy = "content")
    @Comment("내용 로그 리스트")
    List<ContentLog> contentLogList = new ArrayList<>();

    public Content() {
    }

    @Builder
    public Content(String content_subject, String content_detail, Category category, List<ContentLog> contentLogList) {
        this.content_subject = content_subject;
        this.content_detail = content_detail;
        this.category = category;
        this.contentLogList = contentLogList;
    }
}
