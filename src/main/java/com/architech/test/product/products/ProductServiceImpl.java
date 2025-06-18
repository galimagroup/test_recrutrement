package com.architech.test.product.products;

import com.architech.test.product.exception.EntityNotFoundException;
import com.architech.test.product.exception.ResourceNotFoundException;
import com.architech.test.product.exception.StorageException;
import jakarta.validation.ValidationException;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    @Value("${app.upload.dir}")
    private String UPLOAD_DIR;

    // Whitelist of allowed content types
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
        "image/jpeg",
        "image/png",
        "image/gif"
    );

    private final Tika tika = new Tika();

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Request to save a new product {}", product);
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        log.info("Request to find a product by id {}", id);
        return productRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("Product not found with ID: " + id));
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Request to delete a product: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public Product editProduct(Long id, Product productDetails) {
        log.info("Request to update a product with Id: {}", id);

        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            existingProduct.setDescription(productDetails.getDescription());
        }
        if (productDetails.getImage() != null) {
            existingProduct.setImage(productDetails.getImage());
        }
        if (productDetails.getCategory() != null) {
            existingProduct.setCategory(productDetails.getCategory());
        }
        if (productDetails.getPrice() != null) {
            existingProduct.setPrice(productDetails.getPrice());
        }
        if (productDetails.getQuantity() != null) {
            existingProduct.setQuantity(productDetails.getQuantity());
        }
        if (productDetails.getInternalReference() != null) {
            existingProduct.setInternalReference(productDetails.getInternalReference());
        }
        if (productDetails.getShellId() != null) {
            existingProduct.setShellId(productDetails.getShellId());
        }
        if (productDetails.getInventoryStatus() != null) {
            existingProduct.setInventoryStatus(productDetails.getInventoryStatus());
        }
        if (productDetails.getRating() != null) {
            existingProduct.setRating(productDetails.getRating());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public List<Product> getProducts() {
        log.info("Request to get all products");
        return productRepository.findAll();
    }

    @Override
    public Product uploadImage(Long productId, MultipartFile file) {
        log.debug("Request to upload image for product {}", productId);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));

        validateFile(file);

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            Files.setPosixFilePermissions(uploadPath,
                Set.of(PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE));

            String filename = generateSecureFilename(file.getOriginalFilename());

            Path targetPath = storeFile(file, uploadPath, filename);

            product.setImage(filename);
            return productRepository.save(product);

        } catch (IOException e) {
            throw new StorageException("Failed to store image for product " + productId, e);
        }
    }

    private String generateSecureFilename(String originalFilename) {
        String extension = "";
        if (originalFilename != null) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID() + extension;
    }

    private Path storeFile(MultipartFile file, Path uploadPath, String filename) throws IOException {
        Path targetPath = uploadPath.resolve(filename).normalize();

        if (!targetPath.getParent().equals(uploadPath)) {
            throw new SecurityException("Cannot store file outside upload directory");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        Files.setPosixFilePermissions(targetPath,
            Set.of(PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE));

        return targetPath;
    }

    private void validateFile(MultipartFile file) {
        log.debug("Request to store image {}.", file);
        if (file.isEmpty()) {
            throw new ValidationException("Failed to store empty file");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ValidationException("File size exceeds maximum limit");
        }

        try {
            String detectedType = tika.detect(file.getInputStream());
            if (!ALLOWED_CONTENT_TYPES.contains(detectedType)) {
                throw new ValidationException("File type not allowed");
            }
        } catch (IOException e) {
            throw new ValidationException("Failed to validate file", e);
        }
    }

    @Override
    public Resource getImage(Long userId) throws ResourceNotFoundException {
        log.debug("Request to get image {}", userId);
        Product product = productRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (product.getImage() == null || product.getImage().isEmpty()) {
            throw new RuntimeException("User does not have an image");
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(product.getImage()).normalize();

            if (!filePath.getParent().equals(uploadPath)) {
                throw new SecurityException("Cannot access file outside upload directory");
            }

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("Image not found: " + product.getImage());
            }
        } catch (IOException e) {
            throw new StorageException("Failed to load image", e);
        }
    }
}
