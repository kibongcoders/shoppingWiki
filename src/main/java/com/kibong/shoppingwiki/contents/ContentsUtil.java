package com.kibong.shoppingwiki.contents;

import com.kibong.shoppingwiki.domain.Contents;

import java.util.Optional;

public class ContentsUtil {

    public static Contents nullCheckContents(Optional<Contents> optionalContents){
        return optionalContents.orElseThrow(() -> new NullPointerException("존재하지 않는 콘텐츠 입니다."));
    }
}
