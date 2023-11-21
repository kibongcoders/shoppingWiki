package com.kibong.shoppingwiki.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentsLogMsgDto {

    private String ipAddress;
    private Long userId;
    private Long contentsId;
    private String contentsDetail;

    public ContentsLogMsgDto() {
    }
}
