package com.kibong.shoppingwiki.user.controller;

import com.kibong.shoppingwiki.user.dto.RequestUser;
import com.kibong.shoppingwiki.user.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserBaseResponse signUp(@RequestBody RequestUser requestUser){
        UserBaseResponse userBaseResponse = new UserBaseResponse();

        userBaseResponse.setUserId(userService.signUp(requestUser).getId());

        return userBaseResponse;
    }

    @GetMapping("/getUserInfo/{userId}")
    public String getUserInfo(@PathVariable Long userId){
        return "userId = " + userId;
    }


}

@Data
class UserBaseResponse{

    Long userId;

}
