package com.volkans.avsblog.mapper;

import com.volkans.avsblog.dto.request.PostCreateRequestDto;
import com.volkans.avsblog.dto.response.PostCreateResponseDto;
import com.volkans.avsblog.dto.response.PostGetAllResponseDto;
import com.volkans.avsblog.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPostMapper {

    IPostMapper INSTANCE = Mappers.getMapper(IPostMapper.class);

    @Mapping(target = "id", expression = "java(String.valueOf(post.getId()))")
    @Mapping(target = "sender", expression = "java(post.getUser().getUsername())")
    @Mapping(target = "publishDate", expression = "java(post.getPublishDate().toString())")
    @Mapping(target = "category", expression = "java(post.getCategory().getName())")
    @Mapping(target = "commentCount", expression = "java(String.valueOf(post.getComments().size()))")
    @Mapping(target = "likeCount", expression = "java(String.valueOf(post.getLiker().size()))")
    @Mapping(target = "status", expression = "java(post.getStatus().name())")
    PostGetAllResponseDto getAllPostToDto(Post post);

    @Mapping(target = "id", expression = "java(String.valueOf(post.getId()))")
    @Mapping(target = "sender", expression = "java(post.getUser().getUsername())")
    @Mapping(target = "category", expression = "java(post.getCategory().getName())")
    @Mapping(target = "publishDate", expression = "java(post.getPublishDate().toString())")
    PostCreateResponseDto createPostToDto(Post post);


}
