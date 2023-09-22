package com.kibong.shoppingwiki.contents_category.repository;

import com.kibong.shoppingwiki.contents.dto.ContentsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.kibong.shoppingwiki.domain.QContents.contents;
import static com.kibong.shoppingwiki.domain.QContentsCategory.contentsCategory;

public class ContentsCategoryCustomImpl implements ContentsCategoryCustom {

    private final JPAQueryFactory queryFactory;

    public ContentsCategoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<ContentsDto> searchContents(String searchValue) {
        return Optional.ofNullable(queryFactory.select(Projections.fields(
                        ContentsDto.class,
                        contents.id.as("contentsId"),
                        contents.contentsSubject,
                        contents.contentsDetail,
                        contents.contentsUseYn
                ))
                .from(contentsCategory)
                .join(contentsCategory.contents, contents)
                .where(contents.contentsSubject.eq(searchValue))
                .fetchOne());
    }
}
