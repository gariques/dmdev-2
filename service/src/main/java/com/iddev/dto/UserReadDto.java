package com.iddev.dto;

import com.iddev.enums.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {

    Long id;
    String firstName;
    String lastName;
    String username;
    String password;
    Role role;
}
