package com.iddev.service;

import com.iddev.dto.ClientCreateEditDto;
import com.iddev.dto.ClientReadDto;
import com.iddev.dto.UserCreateEditDto;
import com.iddev.dto.UserReadDto;
import com.iddev.mapper.ClientCreateEditMapper;
import com.iddev.mapper.ClientReadMapper;
import com.iddev.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientReadMapper clientReadMapper;
    private final ClientCreateEditMapper clientCreateEditMapper;

    public List<ClientReadDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientReadMapper::map)
                .toList();
    }

    public Page<ClientReadDto> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientReadMapper::map);
    }

    public Optional<ClientReadDto> findById(Long id) {
        return clientRepository.findById(id)
                .map(clientReadMapper::map);
    }

    @Transactional
    public ClientReadDto create(ClientCreateEditDto clientDto) {
        return Optional.of(clientDto)
                .map(clientCreateEditMapper::map)
                .map(clientRepository::save)
                .map(clientReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ClientReadDto> update(Long id, ClientCreateEditDto clientDto) {
        return clientRepository.findById(id)
                .map(entity -> clientCreateEditMapper.map(clientDto, entity))
                .map(clientRepository::saveAndFlush)
                .map(clientReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return clientRepository.findById(id)
                .map(entity -> {
                    clientRepository.delete(entity);
                    clientRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
