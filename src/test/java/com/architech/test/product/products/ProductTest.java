package com.architech.test.product.products;


import com.architech.test.product.enums.InventoryStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Product getValidProduct() {
        return Product.builder()
            .code("PRD001")
            .name("Laptop")
            .description("A good laptop")
            .image("image.jpg")
            .category("Electronics")
            .price(new BigDecimal("1200.00"))
            .quantity(10)
            .internalReference("INT123")
            .shellId(1L)
            .inventoryStatus(InventoryStatus.INSTOCK)
            .rating(5)
            .build();
    }

    @Test
    void testValidProduct() {
        Product product = getValidProduct();
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertTrue(violations.isEmpty(), "Valid product should not produce violations");
    }

    @Test
    void testBlankCode() {
        Product product = getValidProduct();
        product.setCode(""); // Invalid
        Set<ConstraintViolation<Product>> violations = validator.validate(product, CreateValidationGroup.class);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("code")));
    }

    @Test
    void testBlankName() {
        Product product = getValidProduct();
        product.setName(""); // Invalid
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testNegativePrice() {
        Product product = getValidProduct();
        product.setPrice(new BigDecimal("-100.00")); // Invalid
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }

    @Test
    void testNegativeQuantity() {
        Product product = getValidProduct();
        product.setQuantity(-5); // Invalid
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }
}
