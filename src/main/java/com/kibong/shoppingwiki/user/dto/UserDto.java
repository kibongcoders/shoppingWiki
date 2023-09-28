package com.kibong.shoppingwiki.user.dto;

import lombok.Data;

@Data
public class UserDto {

    private String password;
    private String userEmail;
    private Boolean userUseYn;
    private String userNickname;
}
