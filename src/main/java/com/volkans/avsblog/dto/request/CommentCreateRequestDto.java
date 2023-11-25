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
public class CommentCreateRequestDto {

    @NotBlank(message = "UserId must not be blank!")
    private String userId;

    @NotBlank(message = "Content must not be blank!")
    private String content;

    @NotBlank(message = "PostId must not be blank!")
    private String postId;

}
