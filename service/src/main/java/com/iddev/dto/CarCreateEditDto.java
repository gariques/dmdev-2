package com.iddev.dto;

import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.CarStatus;
import com.iddev.enums.Transmission;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@FieldNameConstants
@Builder
public class CarCreateEditDto {

    CarBrand brand;
    String model;
    Integer manufactureYear;
    CarCategory category;
    Transmission transmission;
    Integer price;
    Boolean isAvailable;
    MultipartFile image;
}
