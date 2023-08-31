package com.iddev.filters;

import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CarFilter {

    CarBrand brand;
    Integer year;
    CarCategory category;
    Integer price;
    Boolean isAvailable;
}
