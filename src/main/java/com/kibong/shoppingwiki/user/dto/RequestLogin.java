package com.kibong.shoppingwiki.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "유저 로그인 DTO")
public class RequestLogin {

    @NotNull(message = "이메일을 입력해 주세요.")
    @Size(min = 2, message = "이메일은 2자 이상으로 입력해 주세요.")
    @Email(message = "이메일 형식에 맞게 입력해 주세요.")
    @Schema(description = "유저 이메일", example = "test@test.com")
    String userEmail;

    @NotNull(message = "패스워드를 입력해 주세요")
    //@Size(min = 8, message = "패스워드는 8자 이상으로 입력해주세요.")
    @Schema(description = "패스워드", example = "1234")
    String password;
}
