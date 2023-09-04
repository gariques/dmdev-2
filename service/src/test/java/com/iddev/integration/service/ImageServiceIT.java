package com.iddev.integration.service;

import com.iddev.integration.IntegrationTestBase;
import com.iddev.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class ImageServiceIT extends IntegrationTestBase {

    @Value("${app.image.bucket}")
    private final String bucket;

    private final ImageService imageService;

    @Test
    void uploadImage() {
        String imagePath = "test.jpeg";
        String content = "test";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        imageService.upload(imagePath, inputStream);

        var actualResult = imageService.get(imagePath);

        assertTrue(actualResult.isPresent());
        assertArrayEquals(actualResult.get(), content.getBytes(StandardCharsets.UTF_8));
    }
}
