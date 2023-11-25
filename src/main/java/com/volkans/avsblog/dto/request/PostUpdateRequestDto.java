package com.volkans.avsblog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostUpdateRequestDto {

    @NotBlank(message = "Title must not be blank!")
    private String title;

    @NotBlank(message = "Content must not be blank!")
    private String content;

    @NotBlank(message = "CategoryId must not be blank!")
    private String categoryId;

}
