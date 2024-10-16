package br.com.samueltorga.spring.controller;

import br.com.samueltorga.spring.config.SecurityConfig;
import br.com.samueltorga.spring.controller.dto.product.FindProductResponse;
import br.com.samueltorga.spring.controller.dto.product.NewProductRequest;
import br.com.samueltorga.spring.controller.dto.product.UpdateProductRequest;
import br.com.samueltorga.spring.repository.entity.Product;
import br.com.samueltorga.spring.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
@Import({SecurityConfig.class})
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProductsReturnsListOfProducts() throws Exception {
        Page<Product> productsPage = Page.empty();
        when(productService.getAllProducts(any(Pageable.class))).thenReturn(productsPage);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productsPage)));
    }

    @Test
    void getProductByIdReturnsProductWhenFound() throws Exception {
        FindProductResponse product = new FindProductResponse();
        when(productService.getProductById(1)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    void getProductByIdReturnsNotFoundWhenNotFound() throws Exception {
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProductReturnsCreatedProduct() throws Exception {
        NewProductRequest request = new NewProductRequest("name", "description", "code", BigDecimal.TEN);
        Product product = new Product();
        when(productService.createProduct(any(NewProductRequest.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }

    @Test
    void updateProductReturnsUpdatedProductWhenFound() throws Exception {
        UpdateProductRequest productDetails = UpdateProductRequest.builder()
                .price(BigDecimal.TEN)
                .productCode("code")
                .name("name")
                .description("description")
                .build();
        Product updatedProduct = new Product();
        when(productService.updateProduct(1, productDetails)).thenReturn(Optional.of(updatedProduct));

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDetails)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedProduct)));
    }

    @Test
    void updateProductReturnsNotFoundWhenNotFound() throws Exception {
        UpdateProductRequest productDetails = UpdateProductRequest.builder()
                .price(BigDecimal.TEN)
                .productCode("code")
                .name("name")
                .description("description")
                .build();
        when(productService.updateProduct(1, productDetails)).thenReturn(Optional.empty());

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDetails)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProductReturnsNoContentWhenDeleted() throws Exception {
        when(productService.deleteProduct(1)).thenReturn(true);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProductReturnsNotFoundWhenNotFound() throws Exception {
        when(productService.deleteProduct(1)).thenReturn(false);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound());
    }
}