package com.toy.brand.command.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.brand.command.application.BrandRegisterService;
import com.toy.brand.command.presentation.dto.BrandRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BrandRegisterController 단위테스트")
class BrandRegisterControllerTest {
    @Mock
    BrandRegisterService brandRegisterService;

    @InjectMocks
    BrandRegisterController controller;

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register_성공테스트() throws Exception {
        BrandRegisterRequest request = new BrandRegisterRequest("BrandName");

        Long brandId = 1L;
        when(brandRegisterService.register(anyString())).thenReturn(brandId);

        mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/brands/" + brandId));

        verify(brandRegisterService).register("BrandName");
    }
}