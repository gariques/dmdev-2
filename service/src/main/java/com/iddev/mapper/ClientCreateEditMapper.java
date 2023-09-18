package com.iddev.mapper;

import com.iddev.dto.ClientCreateEditDto;
import com.iddev.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ClientCreateEditMapper implements Mapper<ClientCreateEditDto, Client> {

    @Override
    public Client map(ClientCreateEditDto fromObject, Client toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Client map(ClientCreateEditDto object) {
        var client = new Client();
        copy(object, client);
        return client;
    }

    private void copy(ClientCreateEditDto object, Client client) {
        client.setAge(object.getAge());
        client.setDriverLicenseNumber(object.getDriverLicenseNumber());
        client.setPassportNumber(object.getPassportNumber());
        client.setPhoneNumber(object.getPhoneNumber());
    }
}
