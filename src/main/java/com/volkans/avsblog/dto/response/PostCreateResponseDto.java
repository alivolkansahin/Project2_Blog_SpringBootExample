package com.volkans.avsblog.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostCreateResponseDto {

    private String id;

    private String title;

    private String content;

    private String sender;

    private String category;

    private String publishDate;
}
