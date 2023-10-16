package com.kibong.shoppingwiki.category.repository;

import com.kibong.shoppingwiki.category.dto.CategoryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.kibong.shoppingwiki.domain.QCategory.category;

public class CategoryCustomImpl implements CategoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CategoryDto getParentCategory(Long parentId) {
        return queryFactory.select(Projections.fields(
                        CategoryDto.class,
                        category.id.as("categoryId"),
                        category.categoryName.as("categoryName")
                )).from(category)
                .where(category.id.eq(parentId)).fetchOne();
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName) {
        return queryFactory.select(Projections.fields(
                        CategoryDto.class,
                        category.id.as("categoryId"),
                        category.categoryName.as("categoryName"),
                        category.parentId.as("parentId"),
                        category.regDate.as("regDate"),
                        category.modDate.as("modDate")
                )).from(category)
                .where(category.categoryName.eq(categoryName))
                .fetchOne();
    }

    @Override
    public List<CategoryDto> getChildCategoryList(Long categoryId) {
        return queryFactory.select(Projections.fields(
                        CategoryDto.class,
                        category.id.as("categoryId"),
                        category.categoryName.as("categoryName"),
                        category.parentId.as("parentId")
                )).from(category)
                .where(category.parentId.eq(categoryId))
                .fetch();
    }
}
