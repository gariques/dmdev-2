package com.iddev.integration.service;

import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.Transmission;
import com.iddev.service.CarService;
import com.iddev.dto.CarCreateEditDto;
import com.iddev.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class CarServiceIT extends IntegrationTestBase {

    private static final Long LEXUS = 3L;

    private final CarService carService;

    @Test
    void findAll() {
        var result = carService.findAll();
        assertThat(result).hasSize(4);
    }

    @Test
    void findById() {
        var maybeCar = carService.findById(LEXUS);
        assertTrue(maybeCar.isPresent());
        maybeCar.ifPresent(car -> assertEquals("LX570", car.getModel()));
    }

    @Test
    void create() {
        CarCreateEditDto carDto = CarCreateEditDto.builder()
                .brand(CarBrand.TOYOTA)
                .model("testmodel")
                .manufactureYear(2020)
                .category(CarCategory.STANDARD)
                .transmission(Transmission.AUTO)
                .price(3500)
                .isAvailable(true)
                .build();
        var actualResult = carService.create(carDto);

        assertSame(carDto.getBrand(), actualResult.getBrand());
        assertEquals(carDto.getModel(), actualResult.getModel());
        assertEquals(carDto.getManufactureYear(), actualResult.getManufactureYear());
        assertSame(carDto.getCategory(), actualResult.getCategory());
        assertSame(carDto.getTransmission(), actualResult.getTransmission());
        assertEquals(carDto.getPrice(), actualResult.getPrice());
        assertSame(carDto.getIsAvailable(), actualResult.getIsAvailable());
    }

    @Test
    void update() {
        CarCreateEditDto carDto = CarCreateEditDto.builder()
                .brand(CarBrand.TOYOTA)
                .model("testmodel")
                .manufactureYear(2020)
                .category(CarCategory.STANDARD)
                .transmission(Transmission.AUTO)
                .price(3500)
                .isAvailable(true)
                .build();

        var actualResult = carService.update(LEXUS, carDto);

        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(car -> {
            assertSame(carDto.getBrand(), car.getBrand());
            assertEquals(carDto.getModel(), car.getModel());
            assertEquals(carDto.getManufactureYear(), car.getManufactureYear());
            assertSame(carDto.getCategory(), car.getCategory());
            assertSame(carDto.getTransmission(), car.getTransmission());
            assertEquals(carDto.getPrice(), car.getPrice());
            assertSame(carDto.getIsAvailable(), car.getIsAvailable());
        });
    }

    @Test
    void delete() {
        assertTrue(carService.delete(LEXUS));
    }

    @Test
    void deleteFailed() {
        assertFalse(carService.delete(999L));
    }
}
