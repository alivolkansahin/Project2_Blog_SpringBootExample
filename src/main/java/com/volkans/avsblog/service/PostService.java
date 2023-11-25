package com.volkans.avsblog.service;

import com.volkans.avsblog.dto.request.PostCreateRequestDto;
import com.volkans.avsblog.dto.request.PostUpdateRequestDto;
import com.volkans.avsblog.dto.response.PostCreateResponseDto;
import com.volkans.avsblog.dto.response.PostGetAllResponseDto;
import com.volkans.avsblog.entity.Category;
import com.volkans.avsblog.entity.Post;
import com.volkans.avsblog.entity.User;
import com.volkans.avsblog.entity.enums.ETextStatus;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.mapper.IPostMapper;
import com.volkans.avsblog.repository.PostRepository;
import com.volkans.avsblog.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService extends ServiceManager<Post,Long> {

    private final PostRepository postRepository;

    private final CategoryService categoryService;

    private final UserService userService;

    public PostService(PostRepository postRepository, CategoryService categoryService, UserService userService) {
        super(postRepository);
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public Boolean existsById(Long id){
        return postRepository.existsById(id);
    }

    public Boolean existsByUserId(Long id){
        return postRepository.existsByUserId(id);
    }

    private Boolean existsByCategoryId(Long id) {
        return postRepository.existsByCategoryId(id);
    }

    public List<PostGetAllResponseDto> getAll() {
        List<Post> posts = findAll();
        if(posts.isEmpty()) throw new AvsBlogException(ErrorType.NO_POSTS_EXIST);
        List<PostGetAllResponseDto> postsDto = new ArrayList<>();
        posts.forEach(post -> postsDto.add(IPostMapper.INSTANCE.getAllPostToDto(post)));
        return postsDto;
    }

    public Post getPostById(Long id) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_ID);
        return findById(id).get();
    }

    public List<Post> getPostByUserId(Long id){
        if(!existsByUserId(id)) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_USER);
        return postRepository.findAllByUserId(id);
    }

    public List<Post> getPostByCategoryId(Long id) {
        if(!existsByCategoryId(id)) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_CATEGORY);
        return postRepository.findAllByCategoryId(id);
    }

    public PostCreateResponseDto create(PostCreateRequestDto dto) {
        Category category = categoryService.getCategoryById(Long.valueOf(dto.getCategoryId()));
        User user = userService.getUserById(Long.valueOf(dto.getUserId()));
        Post post = Post.builder().category(category).user(user)
                .title(dto.getTitle()).content(dto.getContent()).build();
        postRepository.save(post);
        return IPostMapper.INSTANCE.createPostToDto(post);
    }

    public void deletePostById(Long id) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_ID);
        Post post = findById(id).get();
        if(post.getStatus().equals(ETextStatus.ARCHIVED)) throw new AvsBlogException(ErrorType.POST_ALREADY_DELETED);
        post.setStatus(ETextStatus.ARCHIVED);
        save(post);
    }

    public List<PostGetAllResponseDto> getAllByUserId(String userId) {
        if(!userService.existsById(Long.valueOf(userId))) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID);
        List<Post> posts = getPostByUserId(Long.valueOf(userId));
        List<PostGetAllResponseDto> postsDto = new ArrayList<>();
        posts.forEach(post -> postsDto.add(IPostMapper.INSTANCE.getAllPostToDto(post)));
        return postsDto;
    }

    //////// BURAYA BIR METHOD YAZILABILIR COPY PASTE SATIRLAR VAR
    //// AYRICA EXISTSBYID DE FALAN HATAYI NEREDE FIRLATMAK DOĞRU OLUR BİR DÜŞÜNEBİLİRSİN.
    public List<PostGetAllResponseDto> getAllByCategoryId(String categoryId) {
        if(!categoryService.existsById(Long.valueOf(categoryId))) throw new AvsBlogException(ErrorType.CATEGORY_NOT_FOUND_BY_ID);
        List<Post> posts = getPostByCategoryId(Long.valueOf(categoryId));
        List<PostGetAllResponseDto> postsDto = new ArrayList<>();
        posts.forEach(post -> postsDto.add(IPostMapper.INSTANCE.getAllPostToDto(post)));
        return postsDto;
    }


    public List<PostGetAllResponseDto> getAllPostsBySearchParameter(String args) {
        List<Post> posts = findAllByTitleLikeIgnoreCaseOrContentLikeIgnoreCase(args, args);
        if(posts.isEmpty()) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_PARAMETER);
        List<PostGetAllResponseDto> postsDto = new ArrayList<>();
        posts.forEach(post -> postsDto.add(IPostMapper.INSTANCE.getAllPostToDto(post)));
        return postsDto;
    }

    public List<Post> findAllByTitleLikeIgnoreCaseOrContentLikeIgnoreCase(String args1, String args2) {
        return postRepository.findAllByTitleLikeIgnoreCaseOrContentLikeIgnoreCase("%"+args1+"%", "%"+args2+"%");
    }

    public List<PostGetAllResponseDto> getAllPostsByCategoryParameter(String category) {
        if(!categoryService.existsByName(category)) throw new AvsBlogException(ErrorType.CATEGORY_NOT_FOUND_BY_NAME);
        List<Post> posts = postRepository.findAllPostsByCategoryNameNativeQuery(category);
        if(posts.isEmpty()) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_CATEGORY);
        List<PostGetAllResponseDto> postsDto = new ArrayList<>();
        posts.forEach(post -> postsDto.add(IPostMapper.INSTANCE.getAllPostToDto(post)));
        return postsDto;
    }

    public void updatePostById(Long id, PostUpdateRequestDto dto) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_ID);
        if(!categoryService.existsById(Long.valueOf(dto.getCategoryId()))) throw new AvsBlogException(ErrorType.CATEGORY_NOT_FOUND_BY_ID);
        Post post = findById(id).get();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        Category category = categoryService.findById(Long.valueOf(dto.getCategoryId())).get();
        post.setCategory(category);
        saveAndFlush(post);
    }

    public List<PostGetAllResponseDto> getAllByDate() {
        List<Post> posts = postRepository.findAllByOrderByPublishDateDesc();
        if(posts.isEmpty()) throw new AvsBlogException(ErrorType.NO_POSTS_EXIST);
        List<PostGetAllResponseDto> postsDto = new ArrayList<>();
        posts.forEach(post -> postsDto.add(IPostMapper.INSTANCE.getAllPostToDto(post)));
        return postsDto;
    }

    public String likePostByUserId(Long postId, Long userId) {
        if(!existsById(postId)) throw new AvsBlogException(ErrorType.POST_NOT_FOUND_BY_ID);
        if(!userService.existsById(Long.valueOf(userId))) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID);
        Post post = findById(postId).get();
        User user = userService.findById(userId).get();
        post.getLiker().add(user);
        saveAndFlush(post);
        return user.getUsername() + " liked post title " + post.getTitle() + " !";
    }
}
