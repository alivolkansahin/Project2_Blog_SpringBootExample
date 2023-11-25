package com.volkans.avsblog.service;

import com.volkans.avsblog.dto.request.CommentCreateRequestDto;
import com.volkans.avsblog.dto.request.CommentUpdateRequestDto;
import com.volkans.avsblog.dto.response.CommentCreateResponseDto;
import com.volkans.avsblog.dto.response.CommentGetAllResponseDto;
import com.volkans.avsblog.entity.Comment;
import com.volkans.avsblog.entity.Post;
import com.volkans.avsblog.entity.User;
import com.volkans.avsblog.entity.enums.ETextStatus;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.mapper.ICommentMapper;
import com.volkans.avsblog.repository.CommentRepository;
import com.volkans.avsblog.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService extends ServiceManager<Comment,Long> {

    private final CommentRepository commentRepository;

    private final PostService postService;

    private final UserService userService;

    public CommentService(CommentRepository commentRepository, PostService postService, UserService userService) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Boolean existsById(Long id){
        return commentRepository.existsById(id);
    }

    public List<CommentGetAllResponseDto> getAll() {
        List<Comment> comments = findAll();
        if(comments.isEmpty()) throw new AvsBlogException(ErrorType.NO_COMMENT_EXISTS);
        List<CommentGetAllResponseDto> commentsDto = new ArrayList<>();
        comments.forEach(comment -> commentsDto.add(ICommentMapper.INSTANCE.getAllCommentToDto(comment)));
        return commentsDto;
    }

    public Comment getPostById(Long id) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.COMMENT_NOT_FOUND_BY_ID);
        return findById(id).get();
    }

    public CommentCreateResponseDto create(CommentCreateRequestDto dto) {
        User user = userService.getUserById(Long.valueOf(dto.getUserId()));
        Post post = postService.getPostById(Long.valueOf(dto.getPostId()));
        Comment comment = Comment.builder().user(user).post(post)
                .content(dto.getContent()).build();
        commentRepository.save(comment);
        return ICommentMapper.INSTANCE.createCommentToDto(comment);
    }

    public void deletePostById(Long id) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.COMMENT_NOT_FOUND_BY_ID);
        Comment comment = findById(id).get();
        if(comment.getStatus().equals(ETextStatus.ARCHIVED)) throw new AvsBlogException(ErrorType.COMMENT_ALREADY_DELETED);
        comment.setStatus(ETextStatus.ARCHIVED);
        save(comment);
    }

    public List<CommentGetAllResponseDto> getAllByUserId(String userId) {
        if(!userService.existsById(Long.valueOf(userId))) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID);
        List<Comment> comments = getCommentByUserId(Long.valueOf(userId));
        List<CommentGetAllResponseDto> commentsDto = new ArrayList<>();
        comments.forEach(comment -> commentsDto.add(ICommentMapper.INSTANCE.getAllCommentToDto(comment)));
        return commentsDto;
    }

    public List<Comment> getCommentByUserId(Long id){
        if(!existsByUserId(id)) throw new AvsBlogException(ErrorType.COMMENT_NOT_FOUND_BY_USER);
        return commentRepository.findAllByUserId(id);
    }

    public Boolean existsByUserId(Long id){
        return commentRepository.existsByUserId(id);
    }

    public List<CommentGetAllResponseDto> getAllByPostId(String postId) {
        if(!postService.existsById(Long.valueOf(postId))) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_ID);
        List<Comment> comments = getCommentByPostId(Long.valueOf(postId));
        List<CommentGetAllResponseDto> commentsDto = new ArrayList<>();
        comments.forEach(comment -> commentsDto.add(ICommentMapper.INSTANCE.getAllCommentToDto(comment)));
        return commentsDto;
    }

    public List<Comment> getCommentByPostId(Long id) {
        if(!existsByPostId(id)) throw new AvsBlogException(ErrorType.COMMENT_NOT_FOUND_ON_POST);
        return commentRepository.findAllByPostId(id);
    }

    public Boolean existsByPostId(Long id) {
        return commentRepository.existsByPostId(id);
    }

    public void updatePostById(Long id, CommentUpdateRequestDto dto) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.COMMENT_NOT_FOUND_BY_ID);
        Comment comment = findById(id).get();
        dto.setContent(dto.getContent().toLowerCase());
        comment.setContent(dto.getContent());
        saveAndFlush(comment);
    }

    public String likeCommentByUserId(Long commentId, Long userId) {
        if(!existsById(commentId)) throw new AvsBlogException(ErrorType.COMMENT_NOT_FOUND_BY_ID);
        if(!userService.existsById(Long.valueOf(userId))) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID);
        Comment comment = findById(commentId).get();
        User user = userService.findById(userId).get();
        comment.getLiker().add(user);
        saveAndFlush(comment);
        return user.getUsername() + " liked comment content " + comment.getContent() + " !";
    }
}
