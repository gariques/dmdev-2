package com.iddev.dto;

import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.CarStatus;
import com.iddev.enums.Transmission;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarReadDto {

    Long id;
    CarBrand brand;
    String model;
    Integer manufactureYear;
    CarCategory category;
    Transmission transmission;
    Integer price;
    Boolean isAvailable;
    String image;
}
