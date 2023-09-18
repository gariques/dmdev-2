package com.iddev.mapper;

import com.iddev.dto.ClientReadDto;
import com.iddev.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientReadMapper implements Mapper<Client, ClientReadDto>{

    @Override
    public ClientReadDto map(Client object) {
        return ClientReadDto.builder()
                .id(object.getId())
                .age(object.getAge())
                .driverLicenseNumber(object.getDriverLicenseNumber())
                .passportNumber(object.getPassportNumber())
                .phoneNumber(object.getPhoneNumber())
                .build();
    }
}
