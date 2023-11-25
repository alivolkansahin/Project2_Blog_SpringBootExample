package com.volkans.avsblog.controller;


import com.volkans.avsblog.dto.request.PostCreateRequestDto;
import com.volkans.avsblog.dto.request.PostUpdateRequestDto;
import com.volkans.avsblog.dto.response.PostCreateResponseDto;
import com.volkans.avsblog.dto.response.PostGetAllResponseDto;
import com.volkans.avsblog.entity.Post;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.volkans.avsblog.constant.EndPoint.*;

@RestController
@RequestMapping(ROOT + POST)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(GETALL) // Tüm yazıları listeler.
    public ResponseEntity<List<PostGetAllResponseDto>> getAll(){
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping(GET + "/{postId}") // Belirli bir yazının detaylarını getirir.
    public ResponseEntity<Post> getPostById(@PathVariable String postId){
        if(postId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(postService.getPostById(Long.valueOf(postId)));
    }

    @PostMapping(CREATE) // Yeni bir yazı oluşturur
    public ResponseEntity<PostCreateResponseDto> create(@RequestBody @Valid PostCreateRequestDto dto){
        return ResponseEntity.ok(postService.create(dto));
    }

    @PutMapping("{postId}") // Belirli bir yazının bilgilerini günceller.
    public ResponseEntity updatePostById(@PathVariable String postId, @RequestBody @Valid PostUpdateRequestDto dto){
        if(postId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        postService.updatePostById(Long.valueOf(postId), dto);
        return ResponseEntity.ok("Update Successful!");
    }

    @DeleteMapping(DELETE + "/{postId}") // Belirli bir yazıyı siler.
    public ResponseEntity deletePostById(@PathVariable String postId){
        if(postId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        postService.deletePostById(Long.valueOf(postId));
        return ResponseEntity.ok("Delete Successful! (Status Changed to \"ARCHIVED\")");
    }

    @GetMapping(USER + "/{userId}") // Belirli bir kullanıcının yazılarını listeler.
    public ResponseEntity<List<PostGetAllResponseDto>> getAllByUserId(@PathVariable String userId){
        if(userId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(postService.getAllByUserId(userId));
    }

    @GetMapping(CATEGORY + "/{categoryId}") // Belirli bir kategoriye ait yazıları listeler.
    public ResponseEntity<List<PostGetAllResponseDto>> getAllByCategoryId(@PathVariable String categoryId){
        if(categoryId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(postService.getAllByCategoryId(categoryId));
    }

    @GetMapping // proje pdfinde aynı path üzerinden verildiği için ikisi de bu şekile çevrildi.
    public ResponseEntity<List<PostGetAllResponseDto>> getAllPosts(@RequestParam(required = false) String search, @RequestParam(required = false) String category) {
        if (search != null) return ResponseEntity.ok(postService.getAllPostsBySearchParameter(search)); // Belirli bir kelimeye göre yazıları arar.
        else if (category != null) return ResponseEntity.ok(postService.getAllPostsByCategoryParameter(category)); // Belirli bir kategoriye ait yazıları getirir.
        else throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
    }
    /*    @GetMapping   // Belirli bir kelimeye göre yazıları arar.
    public ResponseEntity<List<PostGetAllResponseDto>> getAllPostsBySearchParameter(@RequestParam String search){
        return ResponseEntity.ok(postService.getAllPostsBySearchParameter(search));
    }
    @GetMapping   // Belirli bir kategoriye göre yazıları getirir.
    public ResponseEntity<List<PostGetAllResponseDto>> getAllPostsByCategoryParameter(@RequestParam String category){
        return ResponseEntity.ok(postService.getAllPostsByCategoryParameter(category));
    }*/

    @GetMapping(GETALL + "/orderbydate")   // Yazıları yayın tarihine göre sıralama seçeneği
    public ResponseEntity<List<PostGetAllResponseDto>> getAllByDate(){
        return ResponseEntity.ok(postService.getAllByDate());
    }

    @PostMapping(LIKE + "/{postId}")
    public ResponseEntity likePostByUserId(@PathVariable String postId, String likerUserId){
        if(postId == null || likerUserId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok("Like Successful! " + postService.likePostByUserId(Long.valueOf(postId),Long.valueOf(likerUserId)));
    }

}
