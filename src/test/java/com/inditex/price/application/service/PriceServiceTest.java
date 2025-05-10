package com.inditex.price.application.service;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.port.out.FindPricePort;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link PriceService}, verifying the business logic
 * for selecting the most applicable price based on brand, product, and application date.
 */
@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    private static final long BRAND_ID = 1L;
    private static final long PRODUCT_ID = 35455L;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0);
    private static final String CURRENCY = "EUR";

    @Mock
    private FindPricePort repository;

    @InjectMocks
    private PriceService sut;

    /**
     * Scenario:
     *   Multiple prices are returned by the repository for a given product, brand, and date.
     *   Prices differ by priority.
     *
     * Expectation:
     *   The service should return the one with the highest priority.
     */
    @Test
    void givenValidInput_whenPriceListReturned_thenHighestPriorityIsSelected() {

        val lowPriority = getPrice(25.45,1,0);
        val highPriority = getPrice( 35.50,2,1);

        when(repository.findPrices(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .thenReturn(List.of(lowPriority, highPriority));

        // Act
        Optional<Price> result = sut.getPrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(highPriority, result.get());
        verify(repository, times(1)).findPrices(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);
    }

    /**
     * Scenario:
     *   The repository returns an empty list of prices for the given inputs.
     *
     * Expectation:
     *   The service should return Optional.empty() indicating no applicable price was found.
     */
    @Test
    void givenValidInput_whenNoPricesFound_thenReturnsEmptyOptional() {

        when(repository.findPrices(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .thenReturn(List.of());

        // Act
        Optional<Price> result = sut.getPrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

        // Assert
        assertFalse(result.isPresent());
        verify(repository, times(1)).findPrices(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);
    }

    /**
     * Scenario:
     *   The repository returns a single matching price for the given inputs.
     *
     * Expectation:
     *   The service should return that price directly without needing to compare priorities.
     */
    @Test
    void givenSinglePriceInList_thenThatPriceIsReturned() {

        val onlyPrice = getPrice(25.45,1,0);

        when(repository.findPrices(BRAND_ID, PRODUCT_ID, APPLICATION_DATE))
                .thenReturn(List.of(onlyPrice));

        // Act
        Optional<Price> result = sut.getPrice(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(onlyPrice, result.get());
    }

    private static Price getPrice(Double price, Integer priceList, Integer priority) {
        return Price.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(priceList)
                .priority(priority)
                .startDate(APPLICATION_DATE.minusHours(1))
                .endDate(APPLICATION_DATE.plusHours(1))
                .price(BigDecimal.valueOf(price))
                .currency(CURRENCY)
                .build();
    }
}
