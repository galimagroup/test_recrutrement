package sow.issa.recrutement.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sow.issa.recrutement.annotation.Authorization;
import sow.issa.recrutement.exceptions.ResourceAlreadyExistException;
import sow.issa.recrutement.exceptions.ResourceNotFoundException;
import sow.issa.recrutement.mappers.ProductMapper;
import sow.issa.recrutement.models.request.ProductRequest;
import sow.issa.recrutement.models.response.ProductResponse;
import sow.issa.recrutement.repositories.ProductRepository;
import sow.issa.recrutement.services.ProductService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final String PRODUCT_NOT_FOUND = "Produit avec l'id %s n'existe pas!";
    private static final String PRODUCT_ALREADY_EXISTS = "Produit avec le code %s existe!";

    @Authorization
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        if(productRepository.existsByCode(productRequest.getCode())) {
            throw new ResourceAlreadyExistException(String.format(PRODUCT_ALREADY_EXISTS, productRequest.getCode()));
        }

        var product = productMapper.asEntity(productRequest);

        var savedProduct = productMapper.asResponse(productRepository.save(product));

        log.info("Product successfully saved {}", savedProduct.getId());

        return savedProduct;
    }

    @Authorization
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        if (productRequest.getCode() != null) product.setCode(productRequest.getCode());
        if (productRequest.getName() != null) product.setName(productRequest.getName());
        if (productRequest.getDescription() != null) product.setDescription(productRequest.getDescription());
        if (productRequest.getImage() != null) product.setImage(productRequest.getImage());
        if (productRequest.getPrice() != null) product.setPrice(productRequest.getPrice());
        if (productRequest.getQuantity() != null) product.setQuantity(productRequest.getQuantity());
        if (productRequest.getInternalReference() != null) product.setInternalReference(productRequest.getInternalReference());
        if (productRequest.getInventoryStatus() != null) product.setInventoryStatus(productRequest.getInventoryStatus());
        if (productRequest.getRating() != null) product.setRating(productRequest.getRating());
        if (productRequest.getCategory() != null) product.setCategory(productRequest.getCategory());
        if (productRequest.getShelfId() != null) product.setShelfId(productRequest.getShelfId());

         var updatedProduct =productRepository.save(product);

        log.info("Product successfully updated {}", updatedProduct.getId());

        return productMapper.asResponse(updatedProduct);
    }

    @Override
    public ProductResponse readProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, id)));

        log.info("Reading Product {}", product.getId());

        return productMapper.asResponse(product);
    }

    @Override
    public Page<ProductResponse> readAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .map(productMapper::asResponse);
    }

    @Authorization
    @Override
    public void removeProduct(Long id) {
        if(Objects.isNull(id)) {
            throw new RuntimeException("L'id ne doit pas être null");
        }
        productRepository.deleteById(id);

        log.info("Product {} successfully deleted", id);
    }
}
