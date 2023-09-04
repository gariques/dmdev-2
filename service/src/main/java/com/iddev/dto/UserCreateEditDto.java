package com.iddev.dto;

import com.iddev.enums.Role;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
@Builder
public class UserCreateReadDto {

    String firstName;
    String lastName;
    String login;
    String password;
    Role role;
}
