package au.com.nab.icommerce.inventory.dto.mapper;

import au.com.nab.icommerce.inventory.domain.model.Product;
import au.com.nab.icommerce.inventory.dto.ProductFullDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Product and its full DTO
 */
@Mapper(componentModel = "spring", uses ={})
public interface ProductFullMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    ProductFullDTO toDto(Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Product toEntity(ProductFullDTO productDTO);
}
