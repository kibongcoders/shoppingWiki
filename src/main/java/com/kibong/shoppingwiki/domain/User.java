package com.kibong.shoppingwiki.domain;

import com.kibong.shoppingwiki.user.UserGrade;
import com.kibong.shoppingwiki.user.dto.RequestUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Column(name = "refresh_token", nullable = false, unique = true)
    @Comment("리프레쉬 토큰")
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<UserContents> userContentsList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    @Comment("유저 등급")
    private UserGrade userGrade;

    public User() {
    }

    @Builder
    public User(
            Long id, String password, String userEmail, Boolean userUseYn, List<UserContents> userContentsList, String userNickname, UserGrade userGrade) {
        this.id = id;
        this.password = password;
        this.userEmail = userEmail;
        this.userUseYn = userUseYn;
        this.userContentsList = userContentsList;
        this.userNickname = userNickname;
        this.userUuid = UUID.randomUUID().toString();
        this.userGrade = userGrade;
    }

    public void updateUser(RequestUser requestUser, PasswordEncoder passwordEncoder){
        this.userNickname = requestUser.getUserNickname();
        if(!passwordEncoder.matches(requestUser.getPassword(), this.password) && !requestUser.getPassword().equals("123456")){
            this.password = passwordEncoder.encode(requestUser.getPassword());
        }
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
