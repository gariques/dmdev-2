package com.iddev.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iddev.entity.Car;
import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.Transmission;
import com.iddev.integration.IntegrationTestBase;
import com.iddev.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class CarRestControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Mock
    private final ImageService imageService;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/api/v1/cars/1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.brand").value("TOYOTA"),
                        jsonPath("$.model").value("Camry"),
                        jsonPath("$.manufactureYear").value(2020),
                        jsonPath("$.category").value("STANDARD"),
                        jsonPath("$.transmission").value("AUTO"),
                        jsonPath("$.price").value(3000),
                        jsonPath("$.isAvailable").value(true)
                );
    }

    @Test
    void create() throws Exception {
        var testmodel = Car.builder()
                .id(1L)
                .brand(CarBrand.BMW)
                .model("testmodel")
                .manufactureYear(2020)
                .category(CarCategory.STANDARD)
                .transmission(Transmission.AUTO)
                .price(3500)
                .isAvailable(true)
                .build();
        var json = objectMapper.writeValueAsString(testmodel);
        mockMvc.perform(post("/api/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        var testmodel = Car.builder()
                .id(1L)
                .brand(CarBrand.BMW)
                .model("testmodel")
                .manufactureYear(2020)
                .category(CarCategory.STANDARD)
                .transmission(Transmission.AUTO)
                .price(3500)
                .isAvailable(false)
                .build();
        var json = objectMapper.writeValueAsString(testmodel);
        doNothing().when(imageService).upload(any(String.class), any());
        var mockMultipartFile = new MockMultipartFile(
                "name", "some-way", "content", "inpusStream".getBytes());

        mockMvc.perform(put("/api/v1/cars/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.brand").value("BMW"),
                        jsonPath("$.model").value("testmodel"),
                        jsonPath("$.manufactureYear").value(2020),
                        jsonPath("$.category").value("STANDARD"),
                        jsonPath("$.transmission").value("AUTO"),
                        jsonPath("$.price").value(3500),
                        jsonPath("$.isAvailable").value(false)
                );
//        Cannot invoke "org.springframework.web.multipart.MultipartFile.isEmpty()" because "image" is null
//        Денис, ловлю такую ошибку, как поправить?
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cars/1"))
                .andExpect(status().isNoContent());
    }
}
