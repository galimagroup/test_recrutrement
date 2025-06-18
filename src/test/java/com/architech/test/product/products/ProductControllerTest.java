package com.architech.test.product.products;

import com.architech.test.product.exception.ResourceNotFoundException;
import com.architech.test.product.utils.Response;
import jakarta.validation.ValidationException;
import org.apache.tika.Tika;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Spy
    private Tika tika = new Tika();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadImageSuccess() {
        Long productId = 1L;
        MockMultipartFile file = new MockMultipartFile(
            "file", "test-image.png", "image/png",
            "fake-image-content".getBytes()
        );

        Product mockProduct = Product.builder()
            .id(productId)
            .image("test-image.png")
            .build();

        when(productService.uploadImage(eq(productId), any(MultipartFile.class))).thenReturn(mockProduct);

        ResponseEntity<Response> response = productController.uploadImage(productId, file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Image uploaded successfully"));
        assertNotNull(response.getBody().getData());
    }

    @Test
    void testUploadImageValidationException() {
        Long productId = 1L;
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.pdf", "application/pdf", "invalid".getBytes()
        );

        when(productService.uploadImage(eq(productId), any(MultipartFile.class)))
            .thenThrow(new ValidationException("File type not allowed"));

        ResponseEntity<Response> response = productController.uploadImage(productId, file);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("File type not allowed"));
    }

    @Test
    void testGetImageSuccess() throws Exception {
        Long productId = 1L;
        byte[] imageBytes = new byte[] {
            (byte) 0x89, 0x50, 0x4E, 0x47,
            0x0D, 0x0A, 0x1A, 0x0A
        };

        ByteArrayResource imageResource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "test-image.png";
            }
        };

        when(productService.getImage(productId)).thenReturn(imageResource);

        ResponseEntity<Resource> response = productController.getImage(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().getContentType());
        assertEquals("image/png", response.getHeaders().getContentType().toString());

        assertEquals("test-image.png", response.getBody().getFilename());
        assertArrayEquals(imageBytes, response.getBody().getContentAsByteArray());
    }

    @Test
    void testGetImageNotFound() {
        Long productId = 1L;

        when(productService.getImage(productId)).thenThrow(new ResourceNotFoundException("Not found"));

        ResponseEntity<Resource> response = productController.getImage(productId);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetImageValidationException() {
        Long productId = 1L;

        when(productService.getImage(productId)).thenThrow(new ValidationException("Invalid"));

        ResponseEntity<Resource> response = productController.getImage(productId);

        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetImageIOException() {
        Long productId = 1L;

        when(productService.getImage(productId)).thenThrow(new RuntimeException(new IOException("IO Error")));

        ResponseEntity<Resource> response = productController.getImage(productId);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
