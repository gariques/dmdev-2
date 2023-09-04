package com.iddev.integration.repository;

import com.iddev.integration.IntegrationTestBase;
import com.iddev.repository.CarRepository;
import com.iddev.repository.UserRepository;
import com.iddev.repository.OrderRepository;
import lombok.RequiredArgsConstructor;


import javax.persistence.EntityManager;


@RequiredArgsConstructor
class OrderRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

}