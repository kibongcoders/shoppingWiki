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
public class Contents extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id", nullable = false)
    @Comment("콘텐츠 아이디")
    private Long id;

    @Column(name = "contents_subject", nullable = false, unique = true)
    @Comment("콘텐츠 제목")
    private String contentsSubject;

    @Column(name = "contents_detail", nullable = false, columnDefinition = "LONGTEXT")
    @Comment("콘텐츠 상세")
    private String contentsDetail;

    @Column(name = "contents_use_yn", nullable = false, columnDefinition = "LONGTEXT")
    @Comment("콘텐츠 사용 여부")
    private Boolean contentsUseYn;

    @OneToMany(mappedBy = "contents")
    private List<ContentsCategory> contentsCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "contents")
    private List<UserContents> userContentsList = new ArrayList<>();

    public Contents() {
    }

    @Builder
    public Contents(String contentsSubject, String contentsDetail, Boolean contentsUseYn, List<ContentsCategory> contentsCategoryList, List<UserContents> userContentsList) {
        this.contentsSubject = contentsSubject;
        this.contentsDetail = contentsDetail;
        this.contentsUseYn = contentsUseYn;
        this.contentsCategoryList = contentsCategoryList;
        this.userContentsList = userContentsList;
    }

    public void updateContents(String contentsDetail){
        this.contentsDetail = contentsDetail;
    }
}
