package au.com.nab.icommerce.inventory.dto.mapper;

import au.com.nab.icommerce.base.dto.EntityMapper;
import au.com.nab.icommerce.inventory.domain.model.Product;
import au.com.nab.icommerce.inventory.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.persistence.ManyToOne;

/**
 * Mapper for the entity Product and its DTO
 */
@Mapper(componentModel = "spring", uses ={})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "quantity", target = "quantity")
    ProductDTO toDto(Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "quantity", target = "quantity")
    Product toEntity(ProductDTO productDTO);

}
