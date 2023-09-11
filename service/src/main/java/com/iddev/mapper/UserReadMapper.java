package com.iddev.mapper;

import com.iddev.dto.UserReadDto;
import com.iddev.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto>{

    @Override
    public UserReadDto map(User object) {
        return UserReadDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .login(object.getLogin())
                .password(object.getPassword())
                .role(object.getRole())
                .build();
    }
}
