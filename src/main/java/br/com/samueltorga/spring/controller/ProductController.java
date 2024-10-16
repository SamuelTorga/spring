package br.com.samueltorga.spring.controller;

import br.com.samueltorga.spring.controller.dto.product.FindProductResponse;
import br.com.samueltorga.spring.controller.dto.product.NewProductRequest;
import br.com.samueltorga.spring.controller.dto.product.UpdatePartiallyProduct;
import br.com.samueltorga.spring.controller.dto.product.UpdateProductRequest;
import br.com.samueltorga.spring.exceptions.ResourceNotFoundException;
import br.com.samueltorga.spring.repository.entity.Product;
import br.com.samueltorga.spring.service.ProductService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PagedModel<Product>> getAllProducts(@PageableDefault(sort = "id") @NotNull final Pageable pageable) {
        Page<Product> allProducts = productService.getAllProducts(pageable);
        return ResponseEntity.ok(new PagedModel<>(allProducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindProductResponse> getProductById(@PathVariable("id") Integer id) {
        Optional<FindProductResponse> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody NewProductRequest newProductRequest) {
        return productService.createProduct(newProductRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody UpdateProductRequest productDetails) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDetails);
        return updatedProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(@PathVariable("id") Integer id, @RequestBody UpdatePartiallyProduct productDetails) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDetails);
        return updatedProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) {
        boolean isDeleted = productService.deleteProduct(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}