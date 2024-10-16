package br.com.samueltorga.spring.controller.dto.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateProductRequest(String name, String description, String productCode, BigDecimal price) {
}