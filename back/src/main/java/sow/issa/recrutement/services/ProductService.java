package sow.issa.recrutement.services;

import org.springframework.data.domain.Page;
import sow.issa.recrutement.models.request.ProductRequest;
import sow.issa.recrutement.models.request.RegisterRequest;
import sow.issa.recrutement.models.request.SignInRequest;
import sow.issa.recrutement.models.response.ProductResponse;
import sow.issa.recrutement.models.response.SignInResponse;

import java.util.Map;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    ProductResponse readProduct(Long id);
    Page<ProductResponse> readAllProducts(int page, int size);
    void removeProduct(Long id);
}
