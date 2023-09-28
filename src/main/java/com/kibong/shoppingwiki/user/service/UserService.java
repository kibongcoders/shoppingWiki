package com.kibong.shoppingwiki.user.service;

import com.kibong.shoppingwiki.domain.User;
import com.kibong.shoppingwiki.user.dto.RequestUser;

public interface UserService {

    User signUp(RequestUser requestUser);
}
