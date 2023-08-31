package com.iddev.http.rest;

import com.iddev.dto.CarCreateEditDto;
import com.iddev.dto.CarReadDto;
import com.iddev.dto.PageResponse;
import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.Transmission;
import com.iddev.filters.CarFilter;
import com.iddev.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarRestController {

    private final CarService carService;

    @GetMapping()
    public PageResponse<CarReadDto> findAll(CarFilter filter, Pageable pageable) {
        var page = carService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public CarReadDto findById(@PathVariable("id") Long id) {
        return carService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarReadDto create(@RequestBody CarCreateEditDto car) {
        return carService.create(car);
    }

    @PutMapping("/{id}/update")
    public CarReadDto update(@PathVariable("id") Long id, @RequestBody CarCreateEditDto car) {
        return carService.update(id, car)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!carService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/carImage")
    public ResponseEntity<byte[]> findCarImage(@PathVariable("id") Long id) {
        return carService.findCarImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
