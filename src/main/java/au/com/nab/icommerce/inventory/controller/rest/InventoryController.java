package au.com.nab.icommerce.inventory.controller.rest;

import au.com.nab.icommerce.base.dto.JsonResponse;
import au.com.nab.icommerce.base.util.HeaderUtil;
import au.com.nab.icommerce.base.util.PaginationUtil;
import au.com.nab.icommerce.inventory.controller.rest.vm.ProductCriteria;
import au.com.nab.icommerce.inventory.domain.model.Product;
import au.com.nab.icommerce.inventory.dto.BasketDTO;
import au.com.nab.icommerce.inventory.dto.ProductDTO;
import au.com.nab.icommerce.inventory.dto.ProductFullDTO;
import au.com.nab.icommerce.inventory.service.ProductService;
import au.com.nab.icommerce.inventory.validation.ProductValidator;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * All REST APIs of Inventory.
 */
@RestController
@RequestMapping(path = "inventory", produces = MediaType.APPLICATION_JSON_VALUE)
public class InventoryController {

    private final Logger log = LoggerFactory.getLogger(InventoryController.class);

    private final ProductService productService;

    private static final String ENTITY_NAME = "product";

    public InventoryController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get product item by Id
     *
     * @param id
     * @return
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<JsonResponse<ProductFullDTO>> getProductById(@PathVariable Long id) throws Exception {
        log.info("REST request to get Product: {}", id);
        return new ResponseEntity<>(new JsonResponse<>(this.productService.getProductById(id)), HttpStatus.OK);
    }

    /**
     * Get list of product item by criteria
     * with example data as below
     * {
     * "name": "test prod",
     * "color": "Green",
     * "startDate": 1595980800000,
     * "endDate": 1595980800000
     * }
     *
     * @param productCriteria
     * @param pageable
     * @return
     */
    @GetMapping("/product/filter-by-criteria")
    public ResponseEntity<JsonResponse<List<Product>>> getProductListBy(@ApiParam ProductCriteria productCriteria, @ApiParam Pageable pageable) {
        log.info("REST request to get a page of product by criteria {}", productCriteria);
        Page<Product> page = this.productService.findByCriteria(productCriteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/product/filter-by-criteria");
        return new ResponseEntity<>(new JsonResponse<>(page.getContent()), headers, HttpStatus.OK);

    }

    /**
     * Update product item
     *
     * @param productDTO
     * @param bindingResult
     */
    @PutMapping("/product")
    public ResponseEntity<JsonResponse<ProductDTO>> updateProductById(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        ProductValidator.validate(bindingResult);
        log.info("REST request to update Product: {}", productDTO);
        ProductDTO product = this.productService.updateProduct(productDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, product.getId().toString()))
                .body(new JsonResponse<>(product));
    }

    /**
     * create Product Item
     *
     * @param product
     * @param bindingResult
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/product")
    public ResponseEntity<JsonResponse<Product>> createNewProduct(@Valid @RequestBody Product product, BindingResult bindingResult) throws URISyntaxException {
        ProductValidator.validate(bindingResult);
        log.info("REST request to update Product: {}", product);
        product = this.productService.save(product);
        return ResponseEntity.created(new URI("/inventory/product/" + product.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, product.getId().toString()))
                .body(new JsonResponse<>(product));
    }

    /**
     * do Product Order Item
     *
     * @param basketDTO
     * @param bindingResult
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/product/do-product-order")
    public ResponseEntity<JsonResponse<Product>> doProductOrder(@Valid @RequestBody BasketDTO basketDTO, BindingResult bindingResult) throws URISyntaxException {
        //TODO need to implement Basket Validator
        log.info("REST request to update Product: {}", basketDTO);
        this.productService.doProductOrder(basketDTO);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, basketDTO.getId().toString()))
                .body(new JsonResponse<>());
    }

    /**
     * delete product Item by id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/product/{id}")
    public ResponseEntity<JsonResponse<Object>> deleteProduct(@PathVariable Long id) {
        log.info("REST request to update Product: {}", id);
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .body(new JsonResponse<>());
    }
}
