package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Table
public class ContentsLog extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_log_id", nullable = false)
    @Comment("카테고리 아이디")
    private Long id;

    @Column(name = "contents_log_ip", nullable = false)
    @Comment("콘텐츠 제목")
    private String contentsLogIp;

    @Column(name = "contents_log_detail", nullable = false, columnDefinition = "LONGTEXT")
    @Comment("콘텐츠 로그 디테일")
    private String contentsLogDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_contents_id")
    private UserContents userContents;

    public ContentsLog() {
    }

    @Builder
    public ContentsLog(String contentsLogIp, String contentsLogDetail, UserContents userContents) {
        this.contentsLogIp = contentsLogIp;
        this.contentsLogDetail = contentsLogDetail;
        this.userContents = userContents;
    }
}
