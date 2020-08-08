package au.com.nab.icommerce.inventory.dto.mapper;

import au.com.nab.icommerce.inventory.domain.model.ProductHistory;
import au.com.nab.icommerce.inventory.dto.ProductHistoryFullDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity ProductHistory and its full DTO
 */
@Mapper(componentModel = "spring", uses ={})
public interface ProductHistoryFullMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "belongToProd.id", target = "belongToProdId")
    ProductHistoryFullDTO toDTO(ProductHistory productHistory);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "belongToProdId", target = "belongToProd.id")
    ProductHistory toEntity(ProductHistoryFullDTO productHistoryDTO);

    default ProductHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductHistory productHistory = new ProductHistory();
        productHistory.setId(id);
        return productHistory;
    }
}
