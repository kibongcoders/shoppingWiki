package com.kibong.shoppingwiki.user.dto;

import com.kibong.shoppingwiki.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "유저 DTO")
public class UserDto {

    @Schema(description = "유저 이메일")
    private String userEmail;

    @Schema(description = "유저 사용 여부")
    private Boolean userUseYn;

    @Schema(description = "유저 닉네임")
    private String userNickname;

    @Schema(description = "유저 Uuid")
    private String userUuid;

    @Schema(description = "유저 Uuid")
    private LocalDate regDate;

    public UserDto() {

    }

    public UserDto(User user) {
        this.userEmail = user.getUserEmail();
        this.userUseYn = user.getUserUseYn();
        this.userNickname = user.getUserNickname();
        this.userUuid = user.getUserUuid();
        this.regDate = LocalDate.from(user.getRegDate());
    }
}
