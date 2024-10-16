package br.com.samueltorga.spring.controller.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FindProductResponse implements Serializable {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("price")
    private BigDecimal price;
}