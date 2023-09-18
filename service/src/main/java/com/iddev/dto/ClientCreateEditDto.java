package com.iddev.dto;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
@Builder
public class ClientCreateEditDto {

    Integer age;
    String driverLicenseNumber;
    String passportNumber;
    String phoneNumber;
}
