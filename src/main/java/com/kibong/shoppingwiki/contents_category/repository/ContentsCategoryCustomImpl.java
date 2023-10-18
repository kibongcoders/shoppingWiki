package com.kibong.shoppingwiki.contents_category.repository;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.kibong.shoppingwiki.domain.Category;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.kibong.shoppingwiki.domain.QCategory.category;
import static com.kibong.shoppingwiki.domain.QContents.contents;
import static com.kibong.shoppingwiki.domain.QContentsCategory.contentsCategory;

public class ContentsCategoryCustomImpl implements ContentsCategoryCustom {

    private final JPAQueryFactory queryFactory;

    public ContentsCategoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ContentsDto searchContents(String searchValue) {
        return queryFactory.select(Projections.fields(
                        ContentsDto.class,
                        contents.id.as("contentsId"),
                        contents.contentsSubject,
                        contents.contentsDetail,
                        contents.contentsUseYn,
                        contents.regDate,
                        contents.modDate
                ))
                .from(contents)
                .where(contents.contentsSubject.eq(searchValue))
                .fetchOne();
    }

    public List<CategoryDto> getCategoryList(Long contentsId){
        return queryFactory.select(
                        Projections.fields(
                                CategoryDto.class,
                                category.id.as("categoryId"),
                                category.categoryName.as("categoryName"),
                                category.parentId.as("parentId"),
                                category.regDate.as("regDate"),
                                category.modDate.as("modDate")
                        ))
                .from(contentsCategory)
                .join(contentsCategory.category, category)
                .join(contentsCategory.contents, contents)
                .where(contents.id.eq(contentsId))
                .fetch();
    }

    public List<ContentsDto> getContentsList(Long categoryId){
        return queryFactory.select(
                        Projections.fields(
                                ContentsDto.class,
                                contents.id.as("contentsId"),
                                contents.contentsSubject.as("contentsSubject")
                        ))
                .from(contentsCategory)
                .join(contentsCategory.category, category)
                .join(contentsCategory.contents, contents)
                .where(category.id.eq(categoryId))
                .fetch();
    }

    public List<Category> getCategoryNameList(Long contentsId){
        return queryFactory
                .select(category)
                .from(contentsCategory)
                .join(contentsCategory.category, category)
                .join(contentsCategory.contents, contents)
                .where(contents.id.eq(contentsId))
                .fetch();
    }
}
