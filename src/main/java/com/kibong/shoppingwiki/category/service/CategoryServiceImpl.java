package com.kibong.shoppingwiki.category.service;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.category.dto.ResponseCategoryDto;
import com.kibong.shoppingwiki.category.repository.CategoryRepository;
import com.kibong.shoppingwiki.contents.repository.ContentsRepository;
import com.kibong.shoppingwiki.contents_category.repository.ContentsCategoryRepository;
import com.kibong.shoppingwiki.contents_log.repository.ContentsLogRepository;
import com.kibong.shoppingwiki.domain.*;
import com.kibong.shoppingwiki.user.repository.UserRepository;
import com.kibong.shoppingwiki.user_contents.repository.UserContentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.kibong.shoppingwiki.contents.ContentsUtil.nullCheckContents;
import static com.kibong.shoppingwiki.user.UserUtil.nullCheckUser;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final UserRepository userRepository;
    private final UserContentsRepository userContentsRepository;
    private final ContentsRepository contentsRepository;
    private final ContentsLogRepository contentsLogRepository;
    private final CategoryRepository categoryRepository;
    private final ContentsCategoryRepository contentsCategoryRepository;

    @Override
    public void addCategory(Long userId, Long contentsId, String categoryName, String contentsLogIp) {

        String contentsLogDetail = "카테고리 " + categoryName + "가 추가되었습니다";

        User user = nullCheckUser(userRepository.findById(userId));
        Contents contents = nullCheckContents(contentsRepository.findById(contentsId));

        Category category = categoryRepository.getCategoryByCategoryName(categoryName)
                .orElseGet(() -> Category.builder().categoryName(categoryName).build());

        ContentsCategory contentsCategory = ContentsCategory.builder().category(category).contents(contents).build();

        UserContents userContents = userContentsRepository.getUserContentsByUserAndContents(user, contents).orElseGet(() -> UserContents.builder()
                .user(user)
                .contents(contents).build());

        ContentsLog contentsLog = ContentsLog.builder()
                .contentsLogIp(contentsLogIp)
                .userContents(userContents)
                .contentsLogDetail(contentsLogDetail).build();

        categoryRepository.save(category);
        contentsCategoryRepository.save(contentsCategory);

        userContentsRepository.save(userContents);
        contentsLogRepository.save(contentsLog);
    }

    @Override
    public ResponseCategoryDto getCategory(String categoryName){
        ResponseCategoryDto responseCategoryDto = new ResponseCategoryDto();

        CategoryDto categoryDto = categoryRepository.getCategoryByName(categoryName);
        responseCategoryDto.setChildCategoryList(categoryRepository.getChildCategoryList(categoryDto.getCategoryId()));
        if(categoryDto.getParentId() != null){
            responseCategoryDto.setParentCategory(categoryRepository.getParentCategory(categoryDto.getParentId()));
        }
        responseCategoryDto.setRelationContentsList(
                contentsCategoryRepository.getContentsList(categoryDto.getCategoryId()));

        responseCategoryDto.setCategory(categoryDto);

        return responseCategoryDto;
    }
}
