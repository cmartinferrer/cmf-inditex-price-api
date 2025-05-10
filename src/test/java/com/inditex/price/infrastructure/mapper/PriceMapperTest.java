package com.inditex.price.infrastructure.mapper;

import com.inditex.price.domain.model.Price;
import com.inditex.price.adapter.in.rest.dto.PriceResponse;
import com.inditex.price.adapter.out.persistence.PriceEntity;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceMapperTest {

    private final PriceMapper mapper = Mappers.getMapper(PriceMapper.class);

    @Test
    @DisplayName("Given a PriceEntity, When mapped to Price domain, Then return correct Price object")
    void toDomain_shouldMapCorrectly() {
        // Given
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(1L);
        entity.setProductId(35455L);
        entity.setPriceList(1);
        entity.setPriority(0);
        entity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        entity.setPrice(new BigDecimal("35.50"));
        entity.setCurrency("EUR");

        // When
        Price price = mapper.toDomain(entity);

        // Then
        assertNotNull(price);
        assertEquals(entity.getBrandId(), price.getBrandId());
        assertEquals(entity.getProductId(), price.getProductId());
        assertEquals(entity.getPriceList(), price.getPriceList());
        assertEquals(entity.getPriority(), price.getPriority());
        assertEquals(entity.getStartDate(), price.getStartDate());
        assertEquals(entity.getEndDate(), price.getEndDate());
        assertEquals(entity.getPrice(), price.getPrice());
        assertEquals(entity.getCurrency(), price.getCurrency());
    }

    @Test
    @DisplayName("Given a Price domain, When mapped to PriceResponse, Then return correct PriceResponse DTO")
    void toResponse_shouldMapCorrectly() {
        // Given
        val price = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(1)
                .priority(0)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        // When
        PriceResponse response = mapper.toResponse(price);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getBrandId());
        assertEquals(35455, response.getProductId());
        assertEquals(1, response.getPriceList());
        assertEquals("EUR", response.getCurrency());
        assertEquals(35.50, response.getPrice());
        assertEquals(LocalDateTime.of(2020, 6, 14, 0, 0), response.getStartDate());
        assertEquals(LocalDateTime.of(2020, 12, 31, 23, 59), response.getEndDate());
    }
}
