package com.iddev.integration.repository;

import com.iddev.integration.IntegrationTestBase;
import com.iddev.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;


@RequiredArgsConstructor
public class ClientRepositoryIT extends IntegrationTestBase {

    private final EntityManager entityManager;
    private final ClientRepository clientRepository;

}
