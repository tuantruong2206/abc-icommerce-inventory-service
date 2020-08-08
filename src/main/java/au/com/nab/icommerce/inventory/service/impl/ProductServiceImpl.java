package au.com.nab.icommerce.inventory.service.impl;

import au.com.nab.icommerce.base.enumeration.MessageCodeEnum;
import au.com.nab.icommerce.base.exception.NABException;
import au.com.nab.icommerce.inventory.controller.rest.vm.ProductCriteria;
import au.com.nab.icommerce.inventory.domain.model.Product;
import au.com.nab.icommerce.inventory.domain.model.ProductHistory;
import au.com.nab.icommerce.inventory.domain.repository.ProductHistoryRepository;
import au.com.nab.icommerce.inventory.domain.repository.ProductRepository;
import au.com.nab.icommerce.inventory.dto.BasketDTO;
import au.com.nab.icommerce.inventory.dto.BasketDetailDTO;
import au.com.nab.icommerce.inventory.dto.ProductDTO;
import au.com.nab.icommerce.inventory.dto.ProductFullDTO;
import au.com.nab.icommerce.inventory.dto.mapper.ProductFullMapper;
import au.com.nab.icommerce.inventory.dto.mapper.ProductMapper;
import au.com.nab.icommerce.inventory.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductFullMapper productFullMapper;
    private final ProductHistoryRepository productHistoryRepository;


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductFullMapper productFullMapper, ProductHistoryRepository productHistoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productFullMapper = productFullMapper;
        this.productHistoryRepository = productHistoryRepository;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product currentproduct = this.productRepository.getOne(productDTO.getId());
        ProductHistory productHistory = ProductHistory.packingProductHistory(currentproduct);
        Product result = this.productRepository.save(this.productMapper.toEntity(productDTO));
        this.productHistoryRepository.save(productHistory);
        return this.productMapper.toDto(result);
    }

    @Override
    public ProductFullDTO getProductById(Long id) {
        Optional<Product> optResult = this.productRepository.findById(id);
        return optResult.isPresent() ? this.productFullMapper.toDto(optResult.get()) : null;
    }

    @Override
    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = this.productRepository.getOne(productDTO.getId());
        product.setPrice(productDTO.getPrice());
        product = this.productRepository.save(product);
        return this.productMapper.toDto(product);
    }

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Page<Product> findByCriteria(ProductCriteria productCriteria, Pageable pageable) {
        return this.productRepository.findProductByCriteria(productCriteria.getName(), productCriteria.getColor(), Instant.ofEpochMilli(productCriteria.getStartDate()), Instant.ofEpochMilli(productCriteria.getEndDate()), pageable);
    }

    @Override
    public void doProductOrder(BasketDTO basketDTO) {
        Set<BasketDetailDTO> basketDetailDTOs = basketDTO.getBasketDetails();
        List<Product> products = this.productRepository.findAllByIdIn(basketDetailDTOs.stream().map(x -> x.getProdId()).collect(Collectors.toList()));
        products.forEach(x -> {
            List<BasketDetailDTO> filteredBasketDetailLst = basketDetailDTOs.stream().filter(y -> y.getProdId().equals(x.getId())).collect(Collectors.toList());
            if (filteredBasketDetailLst.size() != 1)
                throw new NABException(MessageCodeEnum.COMMON_ERROR_001, HttpStatus.BAD_REQUEST);
            BasketDetailDTO basketDetailDTO = filteredBasketDetailLst.get(0);
            if (x.getQuantity() < basketDetailDTO.getQuantity())
                throw new NABException(MessageCodeEnum.COMMON_ERROR_001, HttpStatus.BAD_REQUEST);
            x.setQuantity(x.getQuantity() - basketDetailDTO.getQuantity());
        });
        this.productRepository.saveAll(products);
    }
}
