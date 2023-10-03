package com.kibong.shoppingwiki.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "유저 로그인 DTO")
public class ResponseUserDto {

    private String token;
}
