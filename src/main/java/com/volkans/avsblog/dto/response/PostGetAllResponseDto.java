package com.volkans.avsblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostGetAllResponseDto {

    private String id;

    private String title;

    private String content;

    private String sender;

    private String publishDate;

    private String category;

    private String commentCount;

    private String likeCount;

    private String status;

}
