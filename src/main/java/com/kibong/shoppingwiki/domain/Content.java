package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content_subject;

    @Column(columnDefinition = "LONGTEXT")
    private String content_detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "content")
    List<ContentLog> contentLogList = new ArrayList<>();

    public Content() {
    }

    public Content(String content_subject, String content_detail, Category category, List<ContentLog> contentLogList) {
        this.content_subject = content_subject;
        this.content_detail = content_detail;
        this.category = category;
        this.contentLogList = contentLogList;
    }
}
