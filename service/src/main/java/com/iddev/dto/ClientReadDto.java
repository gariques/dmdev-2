package com.iddev.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientReadDto {

    Long id;
    Integer age;
    String driverLicenseNumber;
    String passportNumber;
    String phoneNumber;
}
