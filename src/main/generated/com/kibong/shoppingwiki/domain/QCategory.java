package com.kibong.shoppingwiki.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -793914161L;

    public static final QCategory category = new QCategory("category");

    public final StringPath category_name = createString("category_name");

    public final ListPath<CategoryProduct, QCategoryProduct> categoryList = this.<CategoryProduct, QCategoryProduct>createList("categoryList", CategoryProduct.class, QCategoryProduct.class, PathInits.DIRECT2);

    public final ListPath<Content, QContent> contentList = this.<Content, QContent>createList("contentList", Content.class, QContent.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

