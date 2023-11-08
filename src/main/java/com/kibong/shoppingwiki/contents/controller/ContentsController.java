package com.kibong.shoppingwiki.contents.controller;

import com.google.common.net.HttpHeaders;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.contents.dto.RequestContents;
import com.kibong.shoppingwiki.contents.service.ContentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contents")
@RequiredArgsConstructor
@Tag(name = "위키 컨텐츠 API", description = "위키 컨텐츠 관련 API")
@Slf4j
public class ContentsController {
    private final ContentsService contentsService;
    private final Environment env;

    @Operation(summary = "컨텐츠 검색", description = "")
    @GetMapping("/searchContents/{searchValue}")
    ContentsDto searchContents(@PathVariable String searchValue) {

        return contentsService.searchContents(searchValue);
    }

    @Operation(summary = "컨텐츠 업데이트", description = "")
    @PutMapping("/updateContents/{contentsId}")
    void updateContents(
            @PathVariable Long contentsId,
            String contentsDetail,
            String categoryName,
            @RequestHeader(HttpHeaders.X_FORWARDED_FOR) String ipAddress
    ) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        contentsService.updateContents(userId, contentsId, contentsDetail, categoryName, ipAddress);
    }
    @Operation(summary = "컨텐츠 생성", description = "")
    @PostMapping("/createContents")
    @ResponseStatus(value = HttpStatus.CREATED)
    void createContents(@RequestBody RequestContents requestContents, HttpServletRequest request) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
        contentsService.createContents(userId, requestContents, request);
    }
}
