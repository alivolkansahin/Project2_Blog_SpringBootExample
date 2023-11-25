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
public class CategoryCreateRequestDto {

    @NotBlank(message = "Name must not be blank!")
    private String name;

    @NotBlank(message = "Description must not be blank!")
    private String description;
}
