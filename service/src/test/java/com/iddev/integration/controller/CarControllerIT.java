package com.iddev.integration.controller;

import com.iddev.entity.Car;
import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.Transmission;
import com.iddev.integration.IntegrationTestBase;
import com.iddev.repository.CarRepository;
import com.iddev.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;


import static com.iddev.dto.CarCreateEditDto.Fields.brand;
import static com.iddev.dto.CarCreateEditDto.Fields.category;
import static com.iddev.dto.CarCreateEditDto.Fields.image;
import static com.iddev.dto.CarCreateEditDto.Fields.isAvailable;
import static com.iddev.dto.CarCreateEditDto.Fields.manufactureYear;
import static com.iddev.dto.CarCreateEditDto.Fields.model;
import static com.iddev.dto.CarCreateEditDto.Fields.price;
import static com.iddev.dto.CarCreateEditDto.Fields.transmission;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class CarControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final CarRepository carRepository;

    @MockBean
    private ImageService imageService;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("car/cars"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/cars")
                        .param(brand, "BMW")
                        .param(model, "testmodel")
                        .param(manufactureYear, "2020")
                        .param(category, "STANDARD")
                        .param(price, "3500")
                        .param(transmission, "AUTO")
                        .param(isAvailable, "true")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/cars/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {
        doNothing().when(imageService).upload(any(String.class), any());
        var multipartFile = new MockMultipartFile(
                "name", "some-way", "content", "inputStream".getBytes());
        mockMvc.perform(multipart("/cars/1/update")
                        .file(image, multipartFile.getBytes())
                        .param(brand, "BMW")
                        .param(model, "testmodel")
                        .param(manufactureYear, "2020")
                        .param(category, "STANDARD")
                        .param(transmission, "AUTO")
                        .param(price, "3500")
                        .param(isAvailable, "true")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/cars/{\\d+}")
                );
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/cars/1/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/cars")
                );

        assertThat(carRepository.findById(1L).isEmpty()).isTrue();
    }
}
