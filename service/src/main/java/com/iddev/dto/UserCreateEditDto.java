package com.iddev.dto;

import com.iddev.enums.Role;
import com.iddev.validation.group.CreateAction;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;

@Value
@FieldNameConstants
@Builder
public class UserCreateEditDto {

    String firstName;
    String lastName;
    String username;
    @NotBlank(groups = CreateAction.class)
    String rawPassword;
    Role role;
}
