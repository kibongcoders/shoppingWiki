package com.kibong.shoppingwiki.contents.controller;

import com.kibong.shoppingwiki.contents_category.service.ContentsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/contents")
@RequiredArgsConstructor
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
        ModelAndView mav = new ModelAndView("contents/write");

        return mav;
    }
}
