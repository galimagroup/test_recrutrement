package com.alten.e_commerce.service;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

import com.alten.e_commerce.dto.ProductDto;
import com.alten.e_commerce.entity.Product;
import com.alten.e_commerce.interfaces.IProduct;
import com.alten.e_commerce.repository.ProductRepository;

@Service
public class ProductService implements IProduct{
    
    final ProductRepository productRepository;

    ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product create(ProductDto productdDto){
            
            Product p = productRepository.save(productdDto.toProduct());
            return p;
    }
    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }
    @Override
    public Product findWithId(Long id){
        return productRepository.findById(id).orElseThrow(() ->
        new RuntimeException("Customer not found!"));
    }
    @Override
    public Product update(Long id,ProductDto productdDto) {
        findWithId(id);
        return productRepository.save(productdDto.toProduct());         
    }
    @Override
    public void delete(Long id){
        productRepository.deleteById(id);
    }
    @Override
    public Product patch(Long id,ProductDto productdDto){
        Product product = productdDto.toProduct();
        Product p = findWithId(id);

        if (Objects.nonNull(product.getCode()) && !"".equalsIgnoreCase(product.getCode())) {
            p.setCode(product.getCode());
        }
        if (Objects.nonNull(product.getName()) && !"".equalsIgnoreCase(product.getName())) {
            p.setName(product.getName());
        }
        if (Objects.nonNull(product.getDescription()) && !"".equalsIgnoreCase(product.getDescription())) {
            p.setDescription(product.getDescription());
        }
        if (Objects.nonNull(product.getImage()) && !"".equalsIgnoreCase(product.getImage())) {
            p.setImage(product.getImage());
        }
        if (Objects.nonNull(product.getCategory()) && !"".equalsIgnoreCase(product.getCategory())) {
            p.setCategory(product.getCategory());
        }
        if (Objects.nonNull(product.getPrice()) && !"".equalsIgnoreCase(product.getPrice().toString())) {
            p.setPrice(product.getPrice());
        }
        if (Objects.nonNull(product.getQuantity()) && !"".equalsIgnoreCase(product.getQuantity().toString())) {
            p.setQuantity(product.getQuantity());
        }
        if (Objects.nonNull(product.getIntervalReference()) && !"".equalsIgnoreCase(product.getIntervalReference())) {
            p.setIntervalReference(product.getIntervalReference());
        }
        if (Objects.nonNull(product.getShellId()) && !"".equalsIgnoreCase(product.getShellId().toString())) {
            p.setShellId(product.getShellId());
        }
        if (Objects.nonNull(product.getRating()) && !"".equalsIgnoreCase(product.getRating().toString())) {
            p.setRating(product.getRating());
        }

        Product save = productRepository.save(p);
        return save;
    }

}
