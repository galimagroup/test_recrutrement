package com.backend.procuct_backend.service;

import com.backend.procuct_backend.dao.IProductRepository;
import com.backend.procuct_backend.dao.IUserRepository;
import com.backend.procuct_backend.dto.Product;
import com.backend.procuct_backend.entitie.ProductEntity;
import com.backend.procuct_backend.entitie.UserEntity;
import com.backend.procuct_backend.exception.AccessDeniedException;
import com.backend.procuct_backend.exception.EntityNotFoundException;
import com.backend.procuct_backend.exception.RequestException;
import com.backend.procuct_backend.mapping.ProductMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ProductService {
    private static final String ADMIN_EMAIL = "admin@admin.com";

    private final IProductRepository iProductRepository;
    private final IUserRepository userRepository;
    private final ProductMapper productMapper;
    private final MessageSource messageSource;

    public ProductService(IProductRepository iProductRepository,
                          IUserRepository userRepository,
                          ProductMapper productMapper,
                          MessageSource messageSource) {
        this.iProductRepository = iProductRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<Product> getProduct() {
        return iProductRepository.findAll()
                .stream()
                .map(productMapper::toProduct)
                .toList();
    }

    @Transactional(readOnly = true)
    public Product getProductId(int id) {
        return productMapper.toProduct(iProductRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                messageSource.getMessage("product.notfound", new Object[]{id}, Locale.getDefault())
                        )));
    }

    @Transactional
    public Product createProduct(Product product) {
        // Vérifier l'accès admin
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!ADMIN_EMAIL.equals(email)) {
            throw new AccessDeniedException(
                    messageSource.getMessage("access.denied", null, Locale.getDefault())
            );
        }

        validateProduct(product);

        if (iProductRepository.existsByCode(product.getCode())) {
            throw new RequestException(
                    messageSource.getMessage("product.code.exists", new Object[]{product.getCode()}, Locale.getDefault()),
                    HttpStatus.CONFLICT
            );
        }

        UserEntity admin = userRepository.findByEmail(ADMIN_EMAIL)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("admin.notfound", null, Locale.getDefault())
                ));

        ProductEntity productEntity = productMapper.fromProduct(product);
        productEntity.setUser(admin);

        return productMapper.toProduct(iProductRepository.save(productEntity));
    }

    @Transactional
    public Product patchProduct(int id, Product product) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!ADMIN_EMAIL.equals(email)) {
            throw new AccessDeniedException(
                    messageSource.getMessage("access.denied", null, null)
            );
        }

        return iProductRepository.findById(id)
                .map(entity -> {
                    validatePartialProduct(product);

                    if (product.getCode() != null && !entity.getCode().equals(product.getCode())) {
                        if (iProductRepository.existsByCode(product.getCode())) {
                            throw new RequestException(
                                    messageSource.getMessage("product.code.exists", new Object[]{product.getCode()}, Locale.getDefault()),
                                    HttpStatus.CONFLICT
                            );
                        }
                    }

                    if (product.getCode() != null) {
                        entity.setCode(product.getCode());
                    }
                    if (product.getName() != null) {
                        entity.setName(product.getName());
                    }
                    if (product.getPrice() != null) {
                        entity.setPrice(product.getPrice());
                    }
                    if (product.getDescription() != null) {
                        entity.setDescription(product.getDescription());
                    }

                    return productMapper.toProduct(iProductRepository.save(entity));
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("product.notfound", new Object[]{id}, Locale.getDefault())
                ));
    }

    @Transactional
    public void deleteProduct(int id) {
        // Vérifier l'accès admin
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!ADMIN_EMAIL.equals(email)) {
            throw new AccessDeniedException(
                    messageSource.getMessage("access.denied", null, Locale.getDefault())
            );
        }

        try {
            if (!iProductRepository.existsById(id)) {
                throw new EntityNotFoundException(
                        messageSource.getMessage("product.notfound", new Object[]{id}, Locale.getDefault())
                );
            }
            iProductRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(
                    messageSource.getMessage("product.error-deletion", new Object[]{id}, Locale.getDefault()),
                    HttpStatus.CONFLICT
            );
        }
    }

    private void validateProduct(Product product) {
        if (product.getCode() == null || product.getCode().isBlank()) {
            throw new RequestException(
                    messageSource.getMessage("product.code.required", null, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new RequestException(
                    messageSource.getMessage("product.name.required", null, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RequestException(
                    messageSource.getMessage("product.price.invalid", null, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validatePartialProduct(Product product) {
        if (product.getCode() != null && product.getCode().isBlank()) {
            throw new RequestException(
                    messageSource.getMessage("product.code.required", null, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (product.getName() != null && product.getName().isBlank()) {
            throw new RequestException(
                    messageSource.getMessage("product.name.required", null, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (product.getPrice() != null && product.getPrice() <= 0) {
            throw new RequestException(
                    messageSource.getMessage("product.price.invalid", null, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}