package com.iddev.integration.repository;

import com.iddev.entity.Car;
import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.filters.CarFilter;
import com.iddev.integration.IntegrationTestBase;
import com.iddev.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CarRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final CarRepository carRepository;

    @Test
    void getAvailableCars() {
        var carGraph = entityManager.createEntityGraph(Car.class);

        var filter = CarFilter.builder()
                .isAvailable(true)
                .build();

        List<Car> carList = carRepository.findAllByFilter(filter, carGraph);

        assertThat(carList).hasSize(3);
        assertThat(carList.get(0).getModel()).isEqualTo("Camry");
        assertThat(carList.get(1).getModel()).isEqualTo("7 series");
        assertThat(carList.get(2).getModel()).isEqualTo("LS 350");
    }

    @Test
    void findCarsByBrand() {
        var carGraph = entityManager.createEntityGraph(Car.class);

        var filter = CarFilter.builder()
                .brand(CarBrand.LEXUS)
                .build();

        var carList = carRepository.findAllByFilter(filter, carGraph);

        assertThat(carList).hasSize(2);
        assertThat(carList.get(0).getModel()).isEqualTo("LX 570");
        assertThat(carList.get(1).getModel()).isEqualTo("LS 350");
    }

    @Test
    void findCarsByYear() {
        var carGraph = entityManager.createEntityGraph(Car.class);

        var filter = CarFilter.builder()
                .year(2023)
                .build();

        var carList = carRepository.findAllByFilter(filter, carGraph);

        assertThat(carList).hasSize(2);
        assertThat(carList.get(0).getModel()).isEqualTo("LX 570");
        assertThat(carList.get(1).getModel()).isEqualTo("LS 350");
    }

    @Test
    void findCarsByCategory() {
        var carGraph = entityManager.createEntityGraph(Car.class);

        var filter = CarFilter.builder()
                .category(CarCategory.STANDARD)
                .build();

        var carList = carRepository.findAllByFilter(filter, carGraph);

        assertThat(carList).hasSize(2);
        assertThat(carList.get(0).getModel()).isEqualTo("Camry");
        assertThat(carList.get(1).getModel()).isEqualTo("7 series");
    }
}