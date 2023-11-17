package com.toy.brand.command.presentation;

import com.toy.brand.command.application.BrandDeleteService;
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

@DisplayName("BrandDeleteController 단위테스트")
class BrandDeleteControllerTest {
    @Mock
    private BrandDeleteService brandDeleteService;

    @InjectMocks
    private BrandDeleteController controller;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void delete_성공테스트() throws Exception {
        Long brandId = 1L;

        mockMvc.perform(delete("/brands/" + brandId))
                .andExpect(status().isNoContent());

        verify(brandDeleteService, times(1)).delete(brandId);
    }
}