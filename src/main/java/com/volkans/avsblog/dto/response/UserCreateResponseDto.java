package com.volkans.avsblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserCreateResponseDto {

    private String id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String registerDate; // registerDate.toString()

}
