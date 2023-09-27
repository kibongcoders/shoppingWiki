package com.kibong.shoppingwiki.contents.controller;

import com.kibong.shoppingwiki.contents_category.service.ContentsCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shoppingwiki/contents")
@RequiredArgsConstructor
@Slf4j
public class ContentsController {
    private final ContentsCategoryService contentsCategoryService;

    @GetMapping("/{searchValue}")
    ModelAndView searchContents(@PathVariable String searchValue){
        ModelAndView mav = new ModelAndView("contents/search");

        mav.addObject("contents", contentsCategoryService.searchContents(searchValue));

        return mav;
    }

    @GetMapping("/write")
    ModelAndView writeContents(){
        ModelAndView mav = new ModelAndView("writeTest");

        return mav;
    }

    @GetMapping("/hello")
    @ResponseBody
    String hello(){
        return "hello";
    }

    @GetMapping("/message")
    @ResponseBody
    String message(@RequestHeader("wiki-request") String wikiRequest){
        log.info("wiki-request = {}", wikiRequest);
        return "wiki message";
    }
}
