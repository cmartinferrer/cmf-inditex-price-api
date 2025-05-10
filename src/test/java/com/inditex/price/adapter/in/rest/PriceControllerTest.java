package com.inditex.price.adapter.in.rest;

import com.inditex.price.application.service.PriceService;
import com.inditex.price.domain.model.Price;
import com.inditex.price.adapter.in.rest.dto.PriceResponse;
import com.inditex.price.infrastructure.mapper.PriceMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    private static final int PRODUCT_ID = 35455;
    private static final int BRAND_ID = 1;
    public static final LocalDateTime DATE = LocalDateTime.of(2020, 6, 14, 10, 0);

    @Mock
    private PriceService service;

    @Mock
    private PriceMapper mapper;

    @InjectMocks
    private PriceController sut;

    @Test
    @DisplayName("Given valid input, when price exists, then return 200 and body")
    void testGetPriceReturnsOk() {
        // Given
        Price domainPrice = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .priority(0)
                .startDate(DATE.minusDays(1))
                .endDate(DATE.plusDays(1))
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        PriceResponse dto = new PriceResponse();
        dto.setPrice(35.50);
        dto.setCurrency("EUR");

        when(service.getPrice(1L, 35455L, DATE)).thenReturn(Optional.of(domainPrice));
        when(mapper.toResponse(domainPrice)).thenReturn(dto);

        // When
        ResponseEntity<PriceResponse> response = sut.getPrice(DATE, PRODUCT_ID, BRAND_ID);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(35.50, response.getBody().getPrice());
        assertEquals("EUR", response.getBody().getCurrency());
    }

    @Test
    @DisplayName("Given valid input, when no price found, then return 404")
    void testGetPriceReturnsNotFound() {
        // When
        when(service.getPrice(1L, 35455L, DATE)).thenReturn(Optional.empty());
        ResponseEntity<PriceResponse> response = sut.getPrice(DATE, PRODUCT_ID, BRAND_ID);

        // Then
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}
