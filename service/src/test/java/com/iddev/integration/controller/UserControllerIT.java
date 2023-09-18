package com.iddev.integration.controller;

import com.iddev.dto.CustomUserDetails;
import com.iddev.dto.UserCreateEditDto;
import com.iddev.entity.Car;
import com.iddev.entity.User;
import com.iddev.enums.CarBrand;
import com.iddev.enums.CarCategory;
import com.iddev.enums.Role;
import com.iddev.enums.Transmission;
import com.iddev.integration.IntegrationTestBase;
import com.iddev.repository.CarRepository;
import com.iddev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.iddev.dto.CarCreateEditDto.Fields.brand;
import static com.iddev.dto.CarCreateEditDto.Fields.category;
import static com.iddev.dto.CarCreateEditDto.Fields.isAvailable;
import static com.iddev.dto.CarCreateEditDto.Fields.manufactureYear;
import static com.iddev.dto.CarCreateEditDto.Fields.model;
import static com.iddev.dto.CarCreateEditDto.Fields.price;
import static com.iddev.dto.CarCreateEditDto.Fields.transmission;
import static com.iddev.dto.UserCreateEditDto.Fields.firstName;
import static com.iddev.dto.UserCreateEditDto.Fields.lastName;
import static com.iddev.dto.UserCreateEditDto.Fields.rawPassword;
import static com.iddev.dto.UserCreateEditDto.Fields.role;
import static com.iddev.dto.UserCreateEditDto.Fields.username;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/users/registration")
                .param(username, "username")
                .param(firstName, "test")
                .param(lastName, "testoff")
                .param(role, "ADMIN")
                .param(rawPassword, "{noop}123")
        )
                .andExpect(model().attributeExists("user"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/registration"));
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(username, "username")
                        .param(firstName, "test")
                        .param(lastName, "testoff")
                        .param(role, "ADMIN")
                        .param(rawPassword, "{noop}123")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/1/update")
                        .param(username, "username")
                        .param(firstName, "test")
                        .param(lastName, "testoff")
                        .param(role, "ADMIN")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );

        var expectedResult = User.builder()
                .id(1L)
                .username("username")
                .firstName("test")
                .lastName("testoff")
                .password("{noop}123")
                .role(Role.ADMIN)
                .build();
        var actualResult = userRepository.findById(expectedResult.getId());

        assertThat(actualResult).isPresent();
        assertEquals(expectedResult, actualResult.get());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/1/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );

        assertThat(userRepository.findById(1L).isEmpty()).isTrue();
    }
}
