package com.kibong.shoppingwiki.contents.controller;

import com.kibong.shoppingwiki.contents_category.service.ContentsCategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
@Slf4j
public class ContentsController {
    private final ContentsCategoryService contentsCategoryService;
    private final Environment env;
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

    @GetMapping("/check")
    public String check(HttpServletRequest request){

        log.info("Server port={}", request.getServerPort());
        log.info("local.server.port={}", env.getProperty("local.server.port"));
        log.info("server.port={}", env.getProperty("server.port"));
        log.info("token.expiration_time={}", env.getProperty("token.expiration_time"));
        log.info("token.secret={}", env.getProperty("token.secret"));
        log.info("profile.value = {}", env.getProperty("profile.value"));

        return String.format("wiki check %s", env.getProperty("local.server.port"));
    }
}
