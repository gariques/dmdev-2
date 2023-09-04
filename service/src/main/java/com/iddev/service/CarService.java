package com.iddev.service;

import com.iddev.dto.CarCreateEditDto;
import com.iddev.dto.CarReadDto;
import com.iddev.entity.Car;
import com.iddev.filters.CarFilter;
import com.iddev.mapper.CarCreateEditMapper;
import com.iddev.mapper.CarReadMapper;
import com.iddev.predicates.QPredicate;
import com.iddev.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityGraph;
import java.util.List;
import java.util.Optional;

import static com.iddev.entity.QCar.car;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarService {

    private final CarRepository carRepository;
    private final CarReadMapper carReadMapper;
    private final CarCreateEditMapper carCreateEditMapper;
    private final ImageService imageService;

    public List<CarReadDto> findAll() {
        return carRepository.findAll().stream()
                .map(carReadMapper::map)
                .toList();
    }

    public Page<CarReadDto> findAll(CarFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getBrand(), car.brand::eq)
                .add(filter.getYear(), car.manufactureYear::eq)
                .add(filter.getCategory(), car.category::eq)
                .add(filter.getIsAvailable(), car.isAvailable::eq)
                .buildAnd();
        return carRepository.findAll(predicate, pageable)
                .map(carReadMapper::map);
    }

    public Optional<CarReadDto> findById(Long id) {
        return carRepository.findById(id)
                .map(carReadMapper::map);
    }

    @Transactional
    public CarReadDto create(CarCreateEditDto carDto) {
        return Optional.of(carDto)
                .map(carCreateEditMapper::map)
                .map(carRepository::save)
                .map(carReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CarReadDto> update(Long id, CarCreateEditDto carDto) {
        return carRepository.findById(id)
                .map(entity -> {
                    uploadImage(carDto.getImage());
                    return carCreateEditMapper.map(carDto, entity);
                })
                .map(carRepository::saveAndFlush)
                .map(carReadMapper::map);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    public Optional<byte[]> findCarImage(Long id) {
        return carRepository.findById(id)
                .map(Car::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public boolean delete(Long id) {
        return carRepository.findById(id)
                .map(entity -> {
                    carRepository.delete(entity);
                    carRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
