package com.iddev.mapper;

import com.iddev.dto.CarCreateEditDto;
import com.iddev.entity.Car;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;

@Component
public class CarCreateEditMapper implements Mapper<CarCreateEditDto, Car> {

    @Override
    public Car map(CarCreateEditDto fromObject, Car toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Car map(CarCreateEditDto object) {
        var car = new Car();
        copy(object, car);

        return car;
    }

    private void copy(CarCreateEditDto object, Car car) {
        car.setBrand(object.getBrand());
        car.setModel(object.getModel());
        car.setManufactureYear(object.getManufactureYear());
        car.setCategory(object.getCategory());
        car.setTransmission(object.getTransmission());
        car.setPrice(object.getPrice());
        car.setIsAvailable(object.getIsAvailable());

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> car.setImage(image.getOriginalFilename()));
    }
}
