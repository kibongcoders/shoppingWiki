package com.kibong.shoppingwiki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    @Comment("유저 아이디")
    private Long id;

    @Column(name = "password", nullable = false)
    @Comment("유저 아이디")
    private String password;

    @Column(name = "user_email", nullable = false, unique = true)
    @Comment("유저 이메일")
    private String userEmail;

    @Column(name = "user_use_yn", nullable = false)
    @Comment("유저 사용 여부")
    private Boolean userUseYn;

    @Column(name = "user_nickname", nullable = false, unique = true)
    @Comment("유저 닉네임")
    private String userNickname;

    @Column(name = "user_uuid", nullable = false, unique = true)
    @Comment("유저 닉네임")
    private String userUuid;

    @OneToMany(mappedBy = "user")
    private List<UserContents> userContentsList = new ArrayList<>();

    public User() {
    }

    @Builder
    public User(Long id, String password, String userEmail, Boolean userUseYn, List<UserContents> userContentsList, String userNickname) {
        this.id = id;
        this.password = password;
        this.userEmail = userEmail;
        this.userUseYn = userUseYn;
        this.userContentsList = userContentsList;
        this.userNickname = userNickname;
        this.userUuid = UUID.randomUUID().toString();
    }
}
