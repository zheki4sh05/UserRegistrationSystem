package com.example.UserRegistrationSystem.service.impl;

import com.example.UserRegistrationSystem.dao.*;
import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.mapper.*;
import com.example.UserRegistrationSystem.model.*;
import com.example.UserRegistrationSystem.service.*;
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
            userRepository.save(user);
            return userMapper.toDto(user);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void deleteById(String userId) {
        Optional<User> userOptional= userRepository.findById(UUID.fromString(userId));
        if(userOptional.isPresent()){
            userRepository.delete(userOptional.get());
        }else{
            throw new EntityNotFoundException();
        }
    }


}
