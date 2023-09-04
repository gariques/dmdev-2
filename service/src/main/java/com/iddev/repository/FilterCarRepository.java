package com.iddev.repository;

import com.iddev.entity.Car;
import com.iddev.filters.CarFilter;

import javax.persistence.EntityGraph;
import java.util.List;

public interface FilterCarRepository {

    List<Car> findAllByFilter(CarFilter filter, EntityGraph<Car> graph);

}
