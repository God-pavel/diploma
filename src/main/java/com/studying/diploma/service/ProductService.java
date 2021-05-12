package com.studying.diploma.service;

import com.studying.diploma.dto.ProductDTO;
import com.studying.diploma.model.Product;
import com.studying.diploma.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ProductService {

    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }


    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }


    public boolean saveProduct(ProductDTO productdto) {
        final Optional<Product> productFromDb = getProductByName(productdto.getName());
        if (productFromDb.isPresent()) {
            log.warn("Product already exist at storage!");
            return false;
        }
        final Product product = Product
                .builder()
                .name(productdto.getName())
                .energy(productdto.getEnergy())
                .build();
        productRepository.save(product);
        log.info("Product was saved. Product name: " + product.getName());
        return true;
    }

    public boolean editProduct(final Product product,
                               final ProductDTO productdto) {
        if (!product.getName().equals(productdto.getName()) && getProductByName(productdto.getName()).isPresent()) {
            log.warn("Product name is not unique!");
            return false;
        }
        product.setEnergy(productdto.getEnergy());
        product.setName(productdto.getName());
        productRepository.save(product);
        log.info(product.getName() + " was edited. New price: " + product.getEnergy());
        return true;
    }
}
