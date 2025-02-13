package com.example.UserRegistrationSystem.service.impl;

import com.example.UserRegistrationSystem.dao.*;
import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.exceptions.*;
import com.example.UserRegistrationSystem.mapper.*;
import com.example.UserRegistrationSystem.model.*;
import com.example.UserRegistrationSystem.service.*;
import com.example.UserRegistrationSystem.util.*;
import jakarta.persistence.*;
import jakarta.transaction.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.util.*;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements IProfileService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IAuthenticationService authenticationService;

    @Override
    public UserDto findById(String userId) {
      Optional<User> userOptional= userRepository.findById(UUID.fromString(userId));
      if(userOptional.isPresent()){
          return userMapper.toDto(userOptional.get());
      }else{
          throw new EntityNotFoundException();
      }

    }
    @Override
    @Transactional
    public UserDto update(UserDto userDto) {
        Optional<User> userOptional= userRepository.findById(UUID.fromString(userDto.getId()));
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setBirthdate(Timestamp.valueOf(userDto.getBirthdate().atStartOfDay()));
            if(!userDto.getPassword().trim().isEmpty()){
                if(userDto.getPassword().length()>5 && userDto.getPassword().length()<21){
                    user.setPassword(new PasswordEncoderWrapper().hash(userDto.getPassword()));
                }else{
                    throw new InvalidCredentialsException("password length must be from 6 to 20");
                }
            }
            userRepository.save(user);
            return userMapper.toDto(user);
        }else{
            throw new EntityNotFoundException("user with such email already exists");
        }
    }

    @Override
    @Transactional
    public UserDto updateEmail(EmailUpdateDto emailUpdateDto, String userId) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(userId));
       User user = authenticationService.checkPassword(emailUpdateDto.getPassword(), userOptional);
        user.setEmail(emailUpdateDto.getEmail());
        userRepository.save(user);
        return userMapper.toDto(userRepository.save(user));
    }


    @Override
    @Transactional
    public UserDto updatePassword(PasswordDto passwordDto, String userId) {

        Optional<User> userOptional = userRepository.findById(UUID.fromString(userId));

            User user = authenticationService.checkPassword(passwordDto.getOldPassword(), userOptional);
            if(passwordDto.isSimilar()){
                String newPassword = passwordDto.getNewPassword();
                if(newPassword.length()>5 &&newPassword.length()<21){
                    user.setPassword(new PasswordEncoderWrapper().hash(newPassword));
                }else{
                    throw new InvalidCredentialsException("password length must be from 6 to 20");
                }
                userRepository.save(user);
                return userMapper.toDto(user);
            }else{
                throw new InvalidCredentialsException("passwords are not similar");
            }
    }



}
