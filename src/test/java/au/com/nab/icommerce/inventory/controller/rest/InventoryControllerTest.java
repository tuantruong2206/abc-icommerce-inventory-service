package au.com.nab.icommerce.inventory.controller.rest;

import au.com.nab.icommerce.base.dto.JsonResponse;
import au.com.nab.icommerce.base.exception.RestResponseEntityExceptionHandler;
import au.com.nab.icommerce.inventory.domain.model.Product;
import au.com.nab.icommerce.inventory.dto.ProductDTO;
import au.com.nab.icommerce.inventory.dto.ProductFullDTO;
import au.com.nab.icommerce.inventory.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import support.BaseControllerTest;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(InventoryController.class)
@EnableSpringDataWebSupport
@TestInstance(Lifecycle.PER_CLASS)
public class InventoryControllerTest extends BaseControllerTest {

    @MockBean
    private ProductServiceImpl productService;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @BeforeAll
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new InventoryController(productService)).setControllerAdvice(new RestResponseEntityExceptionHandler()).setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }

    @Test
    void testGivenProductId_whenGetProduct_thenReturnProduct() throws Exception {
        ProductFullDTO productInit = new ProductFullDTO();
        productInit.setId(10L);
        productInit.setName("iphone xs max 28");
        productInit.setDescription("Apple iphone 2020");
        productInit.setPrice(150.0);
        productInit.setColor("black");
        productInit.setQuantity(10);

        when(productService.getProductById(anyLong()))
                .thenReturn(new ProductFullDTO(10L, "iphone xs max 28", "Apple iphone 2020", 150.0, "black", 10, null, null));

        MvcResult mvcResult  = mockMvc.perform(get("/inventory/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.error.code").value(0))
                .andExpect(jsonPath("$.error.message").value("Success"))
                .andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

        JsonResponse<ProductFullDTO> response = super.mapFromJson(mvcResult.getResponse().getContentAsString(), JsonResponse.class);
        ProductFullDTO productResponse = super.mapFromJson(super.mapToJson(response.getData()), ProductFullDTO.class);
        assertTrue(productResponse.equals(productInit));
    }

    @Test
    void testFilterProduct() throws Exception {
        List<Product> prods = new ArrayList<>();
        prods.add(new Product());
        prods.add(new Product());
        when(productService.findByCriteria(any(), any()))
                .thenReturn(new PageImpl<>(prods));
        MvcResult mvcResult  =  mockMvc.perform(get("/inventory/product/filter-by-criteria")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "id,asc")
                .param("color", "black")
                .param("name", "iphone")
                .param("startDate", "1593582946000")
                .param("endDate", "1598939746000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.error.code").value(0))
                .andExpect(jsonPath("$.error.message").value("Success"))
                .andExpect(jsonPath("$.data").isNotEmpty()).andReturn();

    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/inventory/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.error.code").value(0))
                .andExpect(jsonPath("$.error.message").value("Success"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setColor("White");
        productDTO.setDescription("Updated Description");
        productDTO.setName("Updated Product Name");
        productDTO.setPrice(1000.0);
        productDTO.setQuantity(10);
        when(this.productService.updateProduct(any())).thenReturn(productDTO);
        MvcResult result = mockMvc.perform(put("/inventory/product").content(super.mapToJson(productDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.error.code").value(0))
                .andExpect(jsonPath("$.error.message").value("Success")).andReturn();
        result.getResponse().getStatus();
    }

    //should do more unit test do again code covered over 70~80% with exception scenario
    //TODO
}
