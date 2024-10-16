package br.com.samueltorga.spring.service;

import br.com.samueltorga.spring.config.CacheConfig;
import br.com.samueltorga.spring.controller.dto.product.FindProductResponse;
import br.com.samueltorga.spring.controller.dto.product.NewProductRequest;
import br.com.samueltorga.spring.controller.dto.product.UpdatePartiallyProduct;
import br.com.samueltorga.spring.controller.dto.product.UpdateProductRequest;
import br.com.samueltorga.spring.mappers.ProductMapper;
import br.com.samueltorga.spring.repository.ProductRepository;
import br.com.samueltorga.spring.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Cacheable(value = CacheConfig.PRODUCT_CACHE, cacheManager = "cacheManager")
    public Optional<FindProductResponse> getProductById(Integer id) {
        return productRepository.findById(id).map(productMapper::toFindProductResponse);
    }

    public Product createProduct(NewProductRequest newProductRequest) {
        return productRepository.save(productMapper.toProduct(newProductRequest));
    }

    public Optional<Product> updateProduct(Integer id, UpdateProductRequest productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.name());
            product.setDescription(productDetails.description());
            product.setProductCode(productDetails.productCode());
            product.setPrice(productDetails.price());
            return productRepository.save(product);
        });
    }

    public Optional<Product> updateProduct(Integer id, UpdatePartiallyProduct productDetails) {
        return productRepository.findById(id).map(product -> {
            productMapper.partialUpdateProduct(productDetails, product);
            return productRepository.save(product);
        });
    }

    public boolean deleteProduct(Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return true;
                })
                .orElse(false);
    }
}