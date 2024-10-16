package br.com.samueltorga.spring.mappers;

import br.com.samueltorga.spring.controller.dto.product.FindProductResponse;
import br.com.samueltorga.spring.controller.dto.product.NewProductRequest;
import br.com.samueltorga.spring.controller.dto.product.UpdatePartiallyProduct;
import br.com.samueltorga.spring.controller.dto.product.UpdateProductRequest;
import br.com.samueltorga.spring.repository.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    FindProductResponse toFindProductResponse(Product product);

    Product toProduct(NewProductRequest newProductRequest);

    Product toProduct(UpdateProductRequest updateProductRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateProduct(UpdatePartiallyProduct updateProductRequest, @MappingTarget Product product);


}
