package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table
public class ContentLog extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_log_id", nullable = false)
    @Comment("내용 로그 아이디")
    private Long id;

    @Column(nullable = false)
    @Comment("내용 로그 아이피")
    private String content_log_ip;

    @Column(columnDefinition = "LONGTEXT")
    @Comment("내용 상세 로그")
    private String content_log_detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("내용")
    private Content content;

    public ContentLog() {
    }

    @Builder
    public ContentLog(String content_log_ip, String content_log_detail, Content content) {
        this.content_log_ip = content_log_ip;
        this.content_log_detail = content_log_detail;
        this.content = content;
    }
}
