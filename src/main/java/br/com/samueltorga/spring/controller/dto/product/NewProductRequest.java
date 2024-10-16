package br.com.samueltorga.spring.controller.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record NewProductRequest(

        @NotNull
        @Size(max = 100)
        String name,
        @NotNull
        @Size(max = 100)
        String description,
        @NotNull
        @Size(max = 100)
        String productCode,
        @NotNull
        BigDecimal price) {

}