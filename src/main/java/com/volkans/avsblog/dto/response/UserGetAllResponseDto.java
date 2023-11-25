package com.volkans.avsblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserGetAllResponseDto {

    private String id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String registerDate; // registerDate.toString()

    private String status; // EStatus.name()

    private String postCount; // posts.size()

    private String followerCount; // followers.size()

    private String followingCount; // following.size()

}
