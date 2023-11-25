package com.volkans.avsblog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserUpdateRequestDto {

    @NotBlank(message = "Firstname must not be blank!")
    private String firstName;

    @NotBlank(message = "Lastname must not be blank!")
    private String lastName;

    @NotBlank(message = "Username must not be blank!")
    private String username;

    @NotBlank(message = "Email must not be blank!")
    private String email;

    @NotBlank(message = "Password must not be blank!")
    private String password;

    @Builder.Default
    private String photo = "https://cdn-icons-png.flaticon.com/512/3364/3364044.png";

}
