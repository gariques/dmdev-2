package com.iddev.repository;

import com.iddev.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CarRepository extends JpaRepository<Car, Long>, FilterCarRepository, QuerydslPredicateExecutor<Car> {

}
