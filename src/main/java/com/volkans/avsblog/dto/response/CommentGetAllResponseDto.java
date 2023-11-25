package com.volkans.avsblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentGetAllResponseDto {

    private String id;

    private String sender;

    private String content;

    private String commentDate;

    private String postTitle;

    private String likeCount;

    private String status;
}
