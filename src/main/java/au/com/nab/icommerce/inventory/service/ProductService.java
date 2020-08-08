package au.com.nab.icommerce.inventory.service;

import au.com.nab.icommerce.inventory.controller.rest.vm.ProductCriteria;
import au.com.nab.icommerce.inventory.domain.model.Product;
import au.com.nab.icommerce.inventory.dto.BasketDTO;
import au.com.nab.icommerce.inventory.dto.ProductDTO;
import au.com.nab.icommerce.inventory.dto.ProductFullDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDTO updateProduct(ProductDTO product);

    ProductFullDTO getProductById(Long productId);

    void deleteProduct(Long id);

    ProductDTO save(ProductDTO productDTO);

    Product save(Product product);

    Page<Product> findByCriteria(ProductCriteria productCriteria, Pageable pageable);

    public void doProductOrder(BasketDTO basketDTO);

}
