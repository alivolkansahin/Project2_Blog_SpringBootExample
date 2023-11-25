package com.volkans.avsblog.mapper;

import com.volkans.avsblog.dto.response.CommentCreateResponseDto;
import com.volkans.avsblog.dto.response.CommentGetAllResponseDto;
import com.volkans.avsblog.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICommentMapper {

    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);

    @Mapping(target = "id", expression = "java(String.valueOf(comment.getId()))")
    @Mapping(target = "sender", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "commentDate", expression = "java(comment.getCommentDate().toString())")
    @Mapping(target = "postTitle", expression = "java(comment.getPost().getTitle())")
    @Mapping(target = "likeCount", expression = "java(String.valueOf(comment.getLiker().size()))")
    @Mapping(target = "status", expression = "java(comment.getStatus().name())")
    CommentGetAllResponseDto getAllCommentToDto(Comment comment);

    @Mapping(target = "id", expression = "java(String.valueOf(comment.getId()))")
    @Mapping(target = "postTitle", expression = "java(comment.getPost().getTitle())")
    @Mapping(target = "sender", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "commentDate", expression = "java(comment.getCommentDate().toString())")
    CommentCreateResponseDto createCommentToDto(Comment comment);

}
