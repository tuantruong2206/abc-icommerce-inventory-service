package au.com.nab.icommerce.inventory.service;

import au.com.nab.icommerce.inventory.domain.model.Product;
import au.com.nab.icommerce.inventory.domain.repository.ProductHistoryRepository;
import au.com.nab.icommerce.inventory.domain.repository.ProductRepository;
import au.com.nab.icommerce.inventory.dto.ProductFullDTO;
import au.com.nab.icommerce.inventory.dto.mapper.ProductFullMapper;
import au.com.nab.icommerce.inventory.dto.mapper.ProductMapper;
import au.com.nab.icommerce.inventory.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import support.BaseTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class ProductServiceTest extends BaseTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @Mock
    ProductFullMapper productFullMapper;

    @Mock
    ProductHistoryRepository productHistoryRepository;

    @Mock
    ProductServiceImpl productService;

    @BeforeEach
    void init() {
        productService = new ProductServiceImpl(productRepository, productMapper, productFullMapper, productHistoryRepository);
    }

    //TODO it is better setup H2 memory for testing instead of Mock Data only

    @Test
    void testGetProductById() {
        Product expectedProduct = new Product(1L, "Iphone xs max", "Apple product", 150.1, "black", 10, null, null, null);

        ProductFullDTO expectedProductDTO = new ProductFullDTO(1L, "Iphone xs max", "Apple product", 150.1, "black", 10, null, null);

        when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct));

        when(productFullMapper.toDto(expectedProduct)).thenReturn(expectedProductDTO);

        ProductFullDTO actualProduct = productService.getProductById(1L);

        assertThat(actualProduct).isEqualTo(expectedProductDTO);
    }

    //TODO do unit test for remain scenarios



}
