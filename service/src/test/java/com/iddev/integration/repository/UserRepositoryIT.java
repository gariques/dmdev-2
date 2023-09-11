package com.iddev.integration.repository;

import com.iddev.integration.IntegrationTestBase;
import com.iddev.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;


@RequiredArgsConstructor
public class UserRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

}
