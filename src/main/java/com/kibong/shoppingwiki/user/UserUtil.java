package com.kibong.shoppingwiki.user;

import com.kibong.shoppingwiki.domain.User;

import java.util.Optional;

public class UserUtil {

    public static User nullCheckUser(Optional<User> optionalUser){
        return optionalUser.orElseThrow(() -> new NullPointerException("존재하지 않는 유저입니다."));
    }
}
