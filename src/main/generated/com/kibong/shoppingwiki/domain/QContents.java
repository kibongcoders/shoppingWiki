package com.kibong.shoppingwiki.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContents is a Querydsl query type for Contents
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContents extends EntityPathBase<Contents> {

    private static final long serialVersionUID = -1411747093L;

    public static final QContents contents = new QContents("contents");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final ListPath<ContentsCategory, QContentsCategory> contentsCategoryList = this.<ContentsCategory, QContentsCategory>createList("contentsCategoryList", ContentsCategory.class, QContentsCategory.class, PathInits.DIRECT2);

    public final StringPath contentsDetail = createString("contentsDetail");

    public final ListPath<ContentsLog, QContentsLog> contentsLogList = this.<ContentsLog, QContentsLog>createList("contentsLogList", ContentsLog.class, QContentsLog.class, PathInits.DIRECT2);

    public final StringPath contentsSubject = createString("contentsSubject");

    public final BooleanPath contentsUseYn = createBoolean("contentsUseYn");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QContents(String variable) {
        super(Contents.class, forVariable(variable));
    }

    public QContents(Path<? extends Contents> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContents(PathMetadata metadata) {
        super(Contents.class, metadata);
    }

}

