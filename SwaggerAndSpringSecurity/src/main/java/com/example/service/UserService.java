package com.example.service;

import com.example.dto.userDto.UserDto;
import com.example.dto.userDto.UserInfoDto;
import com.example.dto.userDto.NewUserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(NewUserDto newUserDto);

    List<UserInfoDto> getAllUsers();


    void deleteUserById(Long id);

    UserDto getUserByEmail(String email);
}
