package br.com.samueltorga.spring.controller.dto.product;

import java.math.BigDecimal;

public record UpdatePartiallyProduct(String name, String description, String productCode, BigDecimal price) {
}
