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
public class UserContents extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_contents_id", nullable = false)
    @Comment("유저 콘텐츠 아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    @OneToMany(mappedBy = "userContents")
    private List<ContentsLog> contentsLogList = new ArrayList<>();

    public UserContents() {
    }

    @Builder
    public UserContents(User user, Contents contents, List<ContentsLog> contentsLogList) {
        this.user = user;
        this.contents = contents;
        this.contentsLogList = contentsLogList;
    }
}
