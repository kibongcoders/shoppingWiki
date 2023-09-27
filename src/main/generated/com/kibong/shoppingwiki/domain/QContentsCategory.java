package com.kibong.shoppingwiki.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentsCategory is a Querydsl query type for ContentsCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentsCategory extends EntityPathBase<ContentsCategory> {

    private static final long serialVersionUID = -856196855L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentsCategory contentsCategory = new QContentsCategory("contentsCategory");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final QCategory category;

    public final QContents contents;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QContentsCategory(String variable) {
        this(ContentsCategory.class, forVariable(variable), INITS);
    }

    public QContentsCategory(Path<? extends ContentsCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentsCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentsCategory(PathMetadata metadata, PathInits inits) {
        this(ContentsCategory.class, metadata, inits);
    }

    public QContentsCategory(Class<? extends ContentsCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.contents = inits.isInitialized("contents") ? new QContents(forProperty("contents")) : null;
    }

}

