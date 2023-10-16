package com.kibong.shoppingwiki.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class RequestUser {

    @NotNull(message = "이메일을 입력해 주세요.")
    @Size(min = 2, message = "이메일은 2자 이상으로 입력해 주세요.")
    @Email(message = "이메일 형식에 맞게 입력해 주세요.")
    String userEmail;

    @NotNull(message = "패스워드를 입력해 주세요")
    @Size(min = 8, message = "패스워드는 8자 이상으로 입력해주세요.")
    String password;

    @NotNull(message = "닉네임을 입력해 주세요")
    @Size(min = 2, message = "닉네임은 2자 이상으로 입력해 주세요")
    String userNickname;

    public RequestUser() {
    }
}
