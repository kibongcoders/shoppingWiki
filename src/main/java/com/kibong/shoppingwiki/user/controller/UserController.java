package com.kibong.shoppingwiki.user.controller;

import com.kibong.shoppingwiki.user.dto.RequestLogin;
import com.kibong.shoppingwiki.user.dto.RequestUser;
import com.kibong.shoppingwiki.user.dto.ResponseUserDto;
import com.kibong.shoppingwiki.user.dto.UserDto;
import com.kibong.shoppingwiki.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "유저 API", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "")
    @PostMapping("/login")
    public ResponseUserDto login(
            @RequestParam("userEmail") String userEmail,
            @RequestParam("password") String password
            ) {

        RequestLogin requestUser = new RequestLogin();
        requestUser.setUserEmail(userEmail);
        requestUser.setPassword(password);

        return userService.login(requestUser);
    }

    @Operation(summary = "회원가입", description = "")
    @PostMapping("/signUp")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseUserDto signUp(@RequestBody RequestUser requestUser) {

        return userService.signUp(requestUser);
    }

    @Operation(summary = "유저 업데이트", description = "")
    @PutMapping("/updateUser/{userId}")
    public void updateUser(
            @RequestBody RequestUser requestUser,
            @PathVariable Long userId) {

        Long tokenUserId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!Objects.equals(userId, tokenUserId)){
            throw new IllegalArgumentException("접근할 수 없는 아이디입니다.");
        }

        userService.updateUser(requestUser, userId);
    }


    @Operation(summary = "유저 정보 가져오기", description = "")
    @GetMapping("/getUserInfo/{userId}")
    public UserDto getUserInfo(
            @PathVariable Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long tokenUserId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!Objects.equals(userId, tokenUserId)){
            throw new IllegalArgumentException("접근할 수 없는 아이디입니다.");
        }
        return userService.getUserInfo(userId);
    }

    @Operation(summary = "Token 가져오기", description = "")
    @GetMapping("/getToken/{userId}")
    public ResponseUserDto getToken(
            @PathVariable Long userId,
            String refreshToken
    ) {
        return userService.getToken(userId, refreshToken);
    }
}

@Data
class UserBaseResponse{

    Long userId;
}
