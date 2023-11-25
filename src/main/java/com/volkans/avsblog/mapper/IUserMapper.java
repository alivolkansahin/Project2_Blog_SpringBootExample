package com.volkans.avsblog.mapper;

import com.volkans.avsblog.dto.request.UserCreateRequestDto;
import com.volkans.avsblog.dto.response.UserCreateResponseDto;
import com.volkans.avsblog.dto.response.UserGetAllResponseDto;
import com.volkans.avsblog.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {

    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    User createDtoToUser(UserCreateRequestDto dto);

    @Mapping(target = "id", expression = "java(String.valueOf(user.getId()))")
    @Mapping(target = "registerDate", expression = "java(user.getRegisterDate().toString())")
    UserCreateResponseDto createUserToDto(User user);

    @Mapping(target = "id", expression = "java(String.valueOf(user.getId()))")
    @Mapping(target = "registerDate", expression = "java(user.getRegisterDate().toString())")
    @Mapping(target = "status", expression = "java(user.getStatus().name())")
    @Mapping(target = "postCount", expression = "java(String.valueOf(user.getPosts().size()))")
    @Mapping(target = "followerCount", expression = "java(String.valueOf(user.getFollowers().size()))")
    @Mapping(target = "followingCount", expression = "java(String.valueOf(user.getFollowings().size()))")
    UserGetAllResponseDto getAllUserToDto(User user);

}
