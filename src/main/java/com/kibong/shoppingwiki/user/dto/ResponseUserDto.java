package com.kibong.shoppingwiki.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title ="응답 유저 DTO" ,description = "응답 유저 DTO")
public class ResponseUserDto {

    @Schema(description = "토큰")
    private String token;

    @Schema(description = "리프래쉬 토큰")
    private String refreshToken;

    @Schema(description = "유저 아이디")
    private Long userId;


}
