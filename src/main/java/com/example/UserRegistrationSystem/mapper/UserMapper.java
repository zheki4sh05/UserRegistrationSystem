package com.example.UserRegistrationSystem.mapper;

import com.example.UserRegistrationSystem.dto.*;
import com.example.UserRegistrationSystem.model.*;
import com.example.UserRegistrationSystem.util.*;
import org.mapstruct.*;

import java.sql.*;
import java.time.*;
import java.util.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(user.getId().toString())"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "firstname", target = "firstname"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(source = "password", target = "password"),
            @Mapping(target = "birthdate",  qualifiedByName = "local"),
    })
    UserDto toDto(User user);

    @Named("local")
    default LocalDate local(Timestamp timestamp) {
        return timestamp.toLocalDateTime().toLocalDate();
    }

    @Mappings({
            @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(source = "firstname", target = "firstname"),
            @Mapping(source = "lastname", target = "lastname"),
            @Mapping(target = "password",  qualifiedByName = "hash"),
            @Mapping(target = "birthdate",  qualifiedByName = "date"),
    })
    User mapFrom(SignUpDto user);

    @Named("uuid")
    default UUID uuid() {
       return UUID.randomUUID();
    }

    @Named("date")
    default Timestamp date(LocalDate localDate) {
        return Timestamp.valueOf(localDate.atStartOfDay());
    }

    @Named("hash")
    default String hash(String password) {
        return new PasswordEncoderWrapper().hash(password);
    }


}
