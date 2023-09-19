package com.kibong.shoppingwiki.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentLog is a Querydsl query type for ContentLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentLog extends EntityPathBase<ContentLog> {

    private static final long serialVersionUID = 520675228L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentLog contentLog = new QContentLog("contentLog");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QContent content;

    public final StringPath content_log_detail = createString("content_log_detail");

    public final StringPath content_log_ip = createString("content_log_ip");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QContentLog(String variable) {
        this(ContentLog.class, forVariable(variable), INITS);
    }

    public QContentLog(Path<? extends ContentLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentLog(PathMetadata metadata, PathInits inits) {
        this(ContentLog.class, metadata, inits);
    }

    public QContentLog(Class<? extends ContentLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new QContent(forProperty("content"), inits.get("content")) : null;
    }

}

