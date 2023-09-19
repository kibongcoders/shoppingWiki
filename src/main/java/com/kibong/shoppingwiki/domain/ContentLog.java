package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table
public class ContentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_log_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String content_log_ip;

    @Column(columnDefinition = "LONGTEXT")
    private String content_log_detail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Content content;

    public ContentLog() {
    }

    public ContentLog(String content_log_ip, String content_log_detail, Content content) {
        this.content_log_ip = content_log_ip;
        this.content_log_detail = content_log_detail;
        this.content = content;
    }
}
