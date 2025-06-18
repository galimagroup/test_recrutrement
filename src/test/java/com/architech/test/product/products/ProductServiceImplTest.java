package com.architech.test.product.products;

import com.architech.test.product.enums.InventoryStatus;
import com.architech.test.product.exception.ResourceNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(productService, "UPLOAD_DIR", "uploads");

        product = Product.builder()
            .id(1L)
            .code("PRD001")
            .name("Test Product")
            .description("Testing the Products")
            .price(new BigDecimal("100.00"))
            .quantity(10)
            .inventoryStatus(InventoryStatus.INSTOCK)
            .createdAt(LocalDateTime.now())
            .build();
    }

    @AfterEach
    void cleanUp() throws IOException {
        Path uploadPath = Paths.get("uploads");
        if (Files.exists(uploadPath)) {
            Files.walk(uploadPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }


    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product saved = productService.createProduct(product);

        assertNotNull(saved);
        assertEquals("Test Product", saved.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testFindProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product found = productService.findProductById(1L);

        assertNotNull(found);
        assertEquals("PRD001", found.getCode());
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEditProduct() {
        Product updatedDetails = Product.builder()
            .name("Updated Product")
            .description("Updated Desc")
            .price(new BigDecimal("200.00"))
            .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.editProduct(1L, updatedDetails);

        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Desc", result.getDescription());
        assertEquals(new BigDecimal("200.00"), result.getPrice());
    }

    @Test
    void testGetProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> products = productService.getProducts();

        assertFalse(products.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testEditProduct_NotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            productService.editProduct(2L, product));

        assertEquals("Product not found with id: 2", exception.getMessage());
    }

    @Test
    void testUploadImageSuccess() throws IOException {
        Long productId = 1L;
        Product existingProduct = Product.builder().id(productId).build();

        byte[] imageBytes = new byte[] {
            (byte) 0x89, 0x50, 0x4E, 0x47,
            0x0D, 0x0A, 0x1A, 0x0A
        };

        MockMultipartFile file = new MockMultipartFile(
            "file",
            "image.png",
            "image/png",
            imageBytes
        );

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.uploadImage(productId, file);

        assertNotNull(result.getImage());
        assertTrue(result.getImage().endsWith(".png"));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUploadImageWithInvalidTypeThrowsException() {
        Long productId = 1L;
        Product product = Product.builder().id(productId).build();

        MockMultipartFile invalidFile = new MockMultipartFile(
            "file",
            "malware.exe",
            "application/octet-stream",
            "evil content".getBytes()
        );

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ValidationException exception = assertThrows(ValidationException.class, () ->
            productService.uploadImage(productId, invalidFile)
        );

        assertTrue(exception.getMessage().contains("File type not allowed"));
    }

    @Test
    void testGetImageSuccess() throws Exception {
        String fileName = "test-image.png";
        Long productId = 1L;

        Path uploadPath = Paths.get("uploads");
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, "dummy".getBytes());

        Product product = Product.builder().id(productId).image(fileName).build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Resource image = productService.getImage(productId);

        assertNotNull(image);
        assertTrue(image.exists());
    }

    @Test
    void testGetImageWhenImageDoesNotExistThrowsException() {
        Long productId = 1L;
        Product product = Product.builder().id(productId).image("not-found.jpg").build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(ResourceNotFoundException.class, () ->
            productService.getImage(productId)
        );
    }

}
