package com.kibong.shoppingwiki.user.service;

import com.kibong.shoppingwiki.user.dto.RequestLogin;
import com.kibong.shoppingwiki.user.dto.RequestUser;
import com.kibong.shoppingwiki.user.dto.ResponseUserDto;
import com.kibong.shoppingwiki.user.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.http.HttpRequest;

public interface UserService {

    ResponseUserDto login(RequestLogin requestLogin);

    ResponseUserDto signUp(RequestUser requestUser);

    UserDto getUserInfo(Long userId);

    void updateUser(RequestUser requestUser, Long userId);
}
