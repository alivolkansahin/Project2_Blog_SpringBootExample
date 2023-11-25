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
public class UserCreateRequestDto {
    // mesajlar boş değer olamaz diye türkçeye çevriliyordu ondan mecburi geçici çözüm böyle bırakıldı

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

    @NotBlank(message = "Repassword must not be blank!")
    private String rePassword;


}
