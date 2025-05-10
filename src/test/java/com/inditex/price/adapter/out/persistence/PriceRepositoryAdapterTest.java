package com.inditex.price.adapter.out.persistence;

import com.inditex.price.domain.model.Price;
import com.inditex.price.infrastructure.mapper.PriceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link PriceRepositoryAdapter}, responsible for:
 * - Retrieving matching PriceEntities from JPA.
 * - Mapping them to domain Price objects.
 */
@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

    private static final long PRODUCT_ID = 35455L;
    private static final long BRAND_ID = 1L;
    private static final LocalDateTime APPLICATION_DATE = LocalDateTime.of(2020, 6, 14, 10, 0);

    @Mock
    private JpaPriceRepository repository;

    @Mock
    private PriceMapper mapper;

    @InjectMocks
    private PriceRepositoryAdapter sut;

    private PriceEntity priceEntity;
    private Price mappedPrice;

    @BeforeEach
    void setUp() {
        priceEntity = getPriceEntity();
        mappedPrice = getPrice();
    }

    /**
     * Scenario:
     * There is at least one PriceEntity that matches the brandId, productId, and applicationDate criteria.
     * <p>
     * Expectation:
     * The adapter should return a list with the mapped domain Price object(s).
     */
    @Test
    @DisplayName("Given matching entities, When findPrices is called, Then return mapped Price list")
    void findPrices_shouldReturnList() {
        // Given
        when(repository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                BRAND_ID, PRODUCT_ID, APPLICATION_DATE, APPLICATION_DATE
        )).thenReturn(List.of(priceEntity));

        when(mapper.toDomain(priceEntity)).thenReturn(mappedPrice);

        // When
        List<Price> result = sut.findPrices(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

        // Then
        assertNotNull(result, "Expected non-null result list");
        assertEquals(1, result.size(), "Expected list with one price");
        assertEquals(mappedPrice, result.get(0), "Expected mapped price to match");
    }

    /**
     * Scenario:
     * No PriceEntity matches the given filter criteria.
     * <p>
     * Expectation:
     * The adapter should return an empty list.
     */
    @Test
    @DisplayName("Given no matching entity, When findPrices is called, Then return empty list")
    void findPrices_shouldReturnEmptyList() {
        // Given

        when(repository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                BRAND_ID, PRODUCT_ID, APPLICATION_DATE, APPLICATION_DATE
        )).thenReturn(Collections.emptyList());

        // When
        List<Price> result = sut.findPrices(BRAND_ID, PRODUCT_ID, APPLICATION_DATE);

        // Then
        assertNotNull(result, "Expected non-null list even if empty");
        assertTrue(result.isEmpty(), "Expected empty result list");
    }

    private PriceEntity getPriceEntity() {
        priceEntity = new PriceEntity();
        priceEntity.setBrandId(BRAND_ID);
        priceEntity.setProductId(PRODUCT_ID);
        priceEntity.setPriceList(1);
        priceEntity.setPriority(0);
        priceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        priceEntity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        priceEntity.setPrice(new BigDecimal("35.50"));
        priceEntity.setCurrency("EUR");
        return priceEntity;
    }

    private Price getPrice() {
        return Price.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceList(1)
                .priority(0)
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .price(priceEntity.getPrice())
                .currency(priceEntity.getCurrency())
                .build();
    }
}
