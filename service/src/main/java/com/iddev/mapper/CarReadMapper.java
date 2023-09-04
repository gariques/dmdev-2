package com.iddev.mapper;

import com.iddev.dto.CarReadDto;
import com.iddev.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarReadMapper implements Mapper<Car, CarReadDto> {

    @Override
    public CarReadDto map(Car object) {
        return CarReadDto.builder()
                .id(object.getId())
                .brand(object.getBrand())
                .model(object.getModel())
                .manufactureYear(object.getManufactureYear())
                .category(object.getCategory())
                .transmission(object.getTransmission())
                .price(object.getPrice())
                .isAvailable(object.getIsAvailable())
                .image(object.getImage())
                .build();
    }
}

