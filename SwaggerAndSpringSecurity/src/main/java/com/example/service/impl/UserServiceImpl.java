package com.example.service.impl;

import com.example.dto.userDto.NewUserDto;
import com.example.dto.userDto.UserDto;
import com.example.dto.userDto.UserInfoDto;
import com.example.entity.User;
import com.example.exeption.NotFoundException;
import com.example.repository.UserRepo;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(NewUserDto newUserDto) {
        User user = modelMapper.map(newUserDto, User.class);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return modelMapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        var users = (List<User>) userRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long id) {
        if(userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }else {
            throw new NotFoundException("User with this id not found!");
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User with this mail not found!"));
        return modelMapper.map(user, UserDto.class);
    }
}
