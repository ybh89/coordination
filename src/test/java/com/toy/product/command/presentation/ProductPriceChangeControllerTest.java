package com.toy.product.command.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.product.command.application.ProductPriceChangeService;
import com.toy.product.command.presentation.dto.ProductPriceChangeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProductPriceChangeController 단위테스트")
class ProductPriceChangeControllerTest {
    @Mock
    private ProductPriceChangeService productPriceChangeService;

    @InjectMocks
    private ProductPriceChangeController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void changePrice_성공테스트() throws Exception {
        Long productId = 1L;
        ProductPriceChangeRequest request = new ProductPriceChangeRequest(1000);

        mockMvc.perform(patch("/products/" + productId + "/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(productPriceChangeService, times(1)).change(productId, 1000);
    }
}