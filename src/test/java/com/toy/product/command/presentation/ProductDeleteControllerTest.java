package com.toy.product.command.presentation;

import com.toy.product.command.application.ProductDeleteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProductDeleteController 단위테스트")
class ProductDeleteControllerTest {
    @Mock
    private ProductDeleteService productDeleteService;

    @InjectMocks
    private ProductDeleteController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void delete_성공테스트() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/products/" + productId))
                .andExpect(status().isNoContent());

        verify(productDeleteService, times(1)).delete(productId);
    }
}