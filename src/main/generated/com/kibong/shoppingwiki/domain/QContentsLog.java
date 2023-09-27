package com.kibong.shoppingwiki.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentsLog is a Querydsl query type for ContentsLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentsLog extends EntityPathBase<ContentsLog> {

    private static final long serialVersionUID = -1037808551L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentsLog contentsLog = new QContentsLog("contentsLog");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    public final QContents contents;

    public final StringPath contentsLogDetail = createString("contentsLogDetail");

    public final StringPath contentsLogIp = createString("contentsLogIp");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QContentsLog(String variable) {
        this(ContentsLog.class, forVariable(variable), INITS);
    }

    public QContentsLog(Path<? extends ContentsLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentsLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentsLog(PathMetadata metadata, PathInits inits) {
        this(ContentsLog.class, metadata, inits);
    }

    public QContentsLog(Class<? extends ContentsLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contents = inits.isInitialized("contents") ? new QContents(forProperty("contents")) : null;
    }

}

