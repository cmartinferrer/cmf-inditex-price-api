package com.inditex.price.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityTest {

    @Test
    @DisplayName("Given a PriceEntity, when setting fields, then getters return expected values")
    void testEntityFieldAccessors() {
        // Given
        PriceEntity entity = new PriceEntity();

        Long expectedId = 1L;
        Long expectedBrandId = 1L;
        Long expectedProductId = 35455L;
        Integer expectedPriceList = 1;
        Integer expectedPriority = 0;
        LocalDateTime expectedStart = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime expectedEnd = LocalDateTime.of(2020, 12, 31, 23, 59);
        BigDecimal expectedPrice = new BigDecimal("35.50");
        String expectedCurrency = "EUR";

        // When
        entity.setId(expectedId);
        entity.setBrandId(expectedBrandId);
        entity.setProductId(expectedProductId);
        entity.setPriceList(expectedPriceList);
        entity.setPriority(expectedPriority);
        entity.setStartDate(expectedStart);
        entity.setEndDate(expectedEnd);
        entity.setPrice(expectedPrice);
        entity.setCurrency(expectedCurrency);

        // Then
        assertEquals(expectedId, entity.getId());
        assertEquals(expectedBrandId, entity.getBrandId());
        assertEquals(expectedProductId, entity.getProductId());
        assertEquals(expectedPriceList, entity.getPriceList());
        assertEquals(expectedPriority, entity.getPriority());
        assertEquals(expectedStart, entity.getStartDate());
        assertEquals(expectedEnd, entity.getEndDate());
        assertEquals(expectedPrice, entity.getPrice());
        assertEquals(expectedCurrency, entity.getCurrency());
    }
}