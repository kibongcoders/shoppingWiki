package com.kibong.shoppingwiki.contents_category;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class CategoryTest {

    @Test
    public void categoryTest(){
        String categoryName = "헬로우,아이폰,";
        List<String> categoryNameList = List.of(categoryName.split(","));
        for (String s : categoryNameList) {
            log.info("category={}", s);
        }
    }
}
