package com.iddev.http.controller;

import com.iddev.dto.CarReadDto;
import com.iddev.dto.PageResponse;
import com.iddev.entity.Car;
import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.Transmission;
import com.iddev.filters.CarFilter;
import com.iddev.service.CarService;
import com.iddev.dto.CarCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityGraph;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping()
    public String findAll(Model model, CarFilter filter, @PageableDefault(value = 4) Pageable pageable) {
        var page = carService.findAll(filter, pageable);
        var currentPage = page.getNumber();
        var totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("cars", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("brands", CarBrand.values());
        model.addAttribute("categories", CarCategory.values());
        return "car/cars";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return carService.findById(id)
                .map(car -> {
                    model.addAttribute("car", car);
                    model.addAttribute("brands", CarBrand.values());
                    model.addAttribute("categories", CarCategory.values());
                    model.addAttribute("transmissions", Transmission.values());
                    return "car/car";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(CarCreateEditDto car) {
        return "redirect:/cars/" + carService.create(car).getId();
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, CarCreateEditDto car) {
        return carService.update(id, car)
                .map(it -> "redirect:/cars/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!carService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/cars";
    }
}
