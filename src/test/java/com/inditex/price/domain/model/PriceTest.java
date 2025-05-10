package com.inditex.price.domain.model;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    @DisplayName("Given valid input, when building a Price, then fields should be correctly set")
    void givenValidInput_whenBuildingPrice_thenFieldsAreCorrectlySet() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        Integer priceList = 1;
        Integer priority = 0;
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
        BigDecimal price = new BigDecimal("35.50");
        String currency = "EUR";

        // When
        val priceObj = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(priceList)
                .priority(priority)
                .startDate(startDate)
                .endDate(endDate)
                .price(price)
                .currency(currency)
                .build();

        // Then
        assertNotNull(priceObj);
        assertEquals(brandId, priceObj.getBrandId());
        assertEquals(productId, priceObj.getProductId());
        assertEquals(priceList, priceObj.getPriceList());
        assertEquals(priority, priceObj.getPriority());
        assertEquals(startDate, priceObj.getStartDate());
        assertEquals(endDate, priceObj.getEndDate());
        assertEquals(price, priceObj.getPrice());
        assertEquals(currency, priceObj.getCurrency());
    }
}
