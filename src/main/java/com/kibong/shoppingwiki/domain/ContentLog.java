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

    @Column(name="content_log_ip",nullable = false)
    @Comment("내용 로그 아이피")
    private String contentLogIp;

    @Column(name="content_log_detail", columnDefinition = "LONGTEXT")
    @Comment("내용 상세 로그")
    private String contentLogDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @Comment("내용")
    private Content content;

    public ContentLog() {
    }

    @Builder
    public ContentLog(String contentLogIp, String contentLogDetail, Content content) {
        this.contentLogIp = contentLogIp;
        this.contentLogDetail = contentLogDetail;
        this.content = content;
    }
}
