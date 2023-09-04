package com.iddev.mapper;

import com.iddev.dto.UserCreateReadDto;
import com.iddev.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateReadMapper implements Mapper<UserCreateReadDto, User> {

    @Override
    public User map(UserCreateReadDto object) {
        var user = new User();
        copy(object, user);
        return user;
    }

    @Override
    public User map(UserCreateReadDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(UserCreateReadDto object, User user) {
        user.setFirstName(object.getFirstName());
        user.setLastName(object.getLastName());
        user.setLogin(object.getLogin());
        user.setPassword(object.getPassword());
        user.setRole(object.getRole());
    }
}
