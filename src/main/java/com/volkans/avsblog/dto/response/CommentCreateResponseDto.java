package com.volkans.avsblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentCreateResponseDto {

    private String id;

    private String postTitle;

    private String sender;

    private String content;

    private String commentDate;
}
