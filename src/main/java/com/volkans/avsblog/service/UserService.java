package com.volkans.avsblog.service;

import com.volkans.avsblog.dto.request.UserCreateRequestDto;
import com.volkans.avsblog.dto.request.UserUpdateRequestDto;
import com.volkans.avsblog.dto.response.UserCreateResponseDto;
import com.volkans.avsblog.dto.response.UserGetAllResponseDto;
import com.volkans.avsblog.entity.User;
import com.volkans.avsblog.entity.enums.EUserStatus;
import com.volkans.avsblog.exception.AvsBlogException;
import com.volkans.avsblog.exception.ErrorType;
import com.volkans.avsblog.mapper.IUserMapper;
import com.volkans.avsblog.repository.UserRepository;
import com.volkans.avsblog.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceManager<User,Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public UserCreateResponseDto create(UserCreateRequestDto dto) {
        if(existsByEmail(dto.getEmail().toLowerCase())) throw new AvsBlogException(ErrorType.EMAIL_ALREADY_EXISTS);
        if(existsByUsername(dto.getUsername().toLowerCase())) throw new AvsBlogException(ErrorType.USERNAME_ALREADY_EXISTS);
        if(!dto.getPassword().equals(dto.getRePassword())) throw new AvsBlogException(ErrorType.PASSWORDS_NOT_MATCH);
        User user = IUserMapper.INSTANCE.createDtoToUser(dto);
        userRepository.save(user);
        return IUserMapper.INSTANCE.createUserToDto(user);
    }

    public List<UserGetAllResponseDto> getAll(){
        List<User> users = findAll();
        if(users.isEmpty()) throw new AvsBlogException(ErrorType.NO_USERS_EXIST);
        List<UserGetAllResponseDto> usersDto = new ArrayList<>();
        users.forEach(user -> usersDto.add(IUserMapper.INSTANCE.getAllUserToDto(user)));
        return usersDto;
    }

    public User getUserById(Long id){
        if(!existsById(id)) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID);
        return findById(id).get();
    }

    public void deleteUserById(Long id) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID);
        User user = findById(id).get();
        if(user.getStatus().equals(EUserStatus.DELETED)) throw new AvsBlogException(ErrorType.USER_ALREADY_DELETED);
        user.setStatus(EUserStatus.DELETED);
        save(user);
    }

    public void updateUserById(Long id, UserUpdateRequestDto dto) {
        if(!existsById(id)) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID);
        User user = findById(id).get();
        if(existsByUsername(dto.getUsername().toLowerCase()) && !user.getUsername().equals(dto.getUsername().toLowerCase()))
            throw new AvsBlogException(ErrorType.USERNAME_ALREADY_EXISTS);
        if(existsByEmail(dto.getEmail().toLowerCase()) && !user.getEmail().equals(dto.getEmail().toLowerCase()))
            throw new AvsBlogException(ErrorType.EMAIL_ALREADY_EXISTS);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoto(dto.getPhoto());
        saveAndFlush(user);
    }

    public String followUserById(Long followerUserId, Long targetUserId) {
        if(!existsById(followerUserId)) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID, "No follower user found by this id in database!");
        if(!existsById(targetUserId)) throw new AvsBlogException(ErrorType.USER_NOT_FOUND_BY_ID, "No target user found by this id in database!");
        User followerUser = findById(followerUserId).get();
        User targetUser = findById(targetUserId).get();
        followerUser.getFollowings().add(targetUser);
        saveAndFlush(followerUser);
        return followerUser.getUsername() + " is now following " + targetUser.getUsername() + " !";
    }
}
