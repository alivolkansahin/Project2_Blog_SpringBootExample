package com.volkans.avsblog.controller;

import com.volkans.avsblog.dto.request.UserCreateRequestDto;
import com.volkans.avsblog.dto.request.UserUpdateRequestDto;
import com.volkans.avsblog.dto.response.UserCreateResponseDto;
import com.volkans.avsblog.dto.response.UserGetAllResponseDto;
import com.volkans.avsblog.entity.User;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.volkans.avsblog.constant.EndPoint.*;

@RestController
@RequestMapping(ROOT + USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(GETALL)  // Tüm Kullanıcıları Listeler
    public ResponseEntity<List<UserGetAllResponseDto>> getAll(){
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(GET + "/{userId}") // Belirli bir kullanıcının detayları getirir.
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        if(userId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        return ResponseEntity.ok(userService.getUserById(Long.valueOf(userId)));
    }
    @PostMapping(CREATE) // Yeni Bir Kullanıcı Oluşturur
    public ResponseEntity<UserCreateResponseDto> create(@RequestBody @Valid UserCreateRequestDto dto){
        return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED); // 201
    }

    @PutMapping("{userId}") // Belirli bir kullanıcının bilgilerini günceller.
    public ResponseEntity updateUserById(@PathVariable String userId, @RequestBody @Valid UserUpdateRequestDto dto){
        if(userId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        userService.updateUserById(Long.valueOf(userId), dto);
        return ResponseEntity.ok("Update Successful!");
    }

    @DeleteMapping(DELETE + "/{userId}") // Belirli bir kullanıcıyı siler (statüsünü değiştirir benim kurgumda)
    public ResponseEntity deleteUserById(@PathVariable String userId){
        if(userId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        userService.deleteUserById(Long.valueOf(userId));
        return ResponseEntity.ok("Delete Successful! (Status Changed to \"DELETED\")");
    }


    /*
        Benim kendi kurgularım için denemeler follow user, like post, like comment,
        post comment denemeleri yapıldı aşağıdaki gibi (ve tabiki uygun controllerlarda)
    */
    @PutMapping(FOLLOW+"/{followerUserId}")
    public ResponseEntity followUserById(@PathVariable String followerUserId, String targetUserId){
        if(followerUserId == null || targetUserId == null) throw new AvsBlogException(ErrorType.BLANK_PARAMETER_ENTRY);
        if(followerUserId.equals(targetUserId)) throw new AvsBlogException(ErrorType.BAD_PARAMETERS_FOLLOW_REQUEST);
        return ResponseEntity.ok("Follow Successful! " + userService.followUserById(Long.valueOf(followerUserId),Long.valueOf(targetUserId)));
    }

}
