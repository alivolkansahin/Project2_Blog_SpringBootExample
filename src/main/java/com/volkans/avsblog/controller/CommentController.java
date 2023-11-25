package com.volkans.avsblog.controller;

import com.volkans.avsblog.dto.request.CommentCreateRequestDto;
import com.volkans.avsblog.dto.request.CommentUpdateRequestDto;
import com.volkans.avsblog.dto.response.CommentCreateResponseDto;
import com.volkans.avsblog.dto.response.CommentGetAllResponseDto;
import com.volkans.avsblog.entity.Comment;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.volkans.avsblog.constant.EndPoint.*;


@RestController
@RequestMapping(ROOT + COMMENT)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(GETALL) // Tüm yorumları listeler.
    public ResponseEntity<List<CommentGetAllResponseDto>> getAll(){
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping(GET + "/{commentId}") // Belirli bir commentin detaylarını getirir.
    public ResponseEntity<Comment> getPostById(@PathVariable String commentId){
        if(commentId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(commentService.getPostById(Long.valueOf(commentId)));
    }

    @PostMapping(CREATE) // Yeni bir comment oluşturur
    public ResponseEntity<CommentCreateResponseDto> create(@RequestBody @Valid CommentCreateRequestDto dto){
        return ResponseEntity.ok(commentService.create(dto));
    }

    @PutMapping("{commentId}") // Belirli bir commentin bilgilerini günceller.
    public ResponseEntity updatePostById(@PathVariable String commentId, @RequestBody @Valid CommentUpdateRequestDto dto){
        if(commentId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        commentService.updatePostById(Long.valueOf(commentId), dto);
        return ResponseEntity.ok("Update Successful!");
    }

    @DeleteMapping(DELETE + "/{commentId}") // Belirli bir commenti siler.
    public ResponseEntity deletePostById(@PathVariable String commentId){
        if(commentId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        commentService.deletePostById(Long.valueOf(commentId));
        return ResponseEntity.ok("Delete Successful! (Status Changed to \"ARCHIVED\")");
    }

    @GetMapping(USER + "/{userId}") // Belirli bir kullanıcının commentlerini listeler.
    public ResponseEntity<List<CommentGetAllResponseDto>> getAllByUserId(@PathVariable String userId){
        if(userId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(commentService.getAllByUserId(userId));
    }

    @GetMapping(POST + "/{postId}") // Belirli bir posta ait commentleri listeler.
    public ResponseEntity<List<CommentGetAllResponseDto>> getAllByPostId(@PathVariable String postId){
        if(postId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(commentService.getAllByPostId(postId));
    }

    @PostMapping(LIKE + "/{commentId}")
    public ResponseEntity likePostByUserId(@PathVariable String commentId, String likerUserId){
        if(commentId == null || likerUserId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok("Like Successful! " + commentService.likeCommentByUserId(Long.valueOf(commentId),Long.valueOf(likerUserId)));
    }

}
