package com.inditex.price.adapter.out.persistence;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.port.out.FindPricePort;
import com.inditex.price.infrastructure.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements FindPricePort {

    private final JpaPriceRepository jpaRepo;
    private final PriceMapper mapper;

    @Override
    public List<Price> findPrices(Long brandId, Long productId, LocalDateTime applicationDate) {
        return jpaRepo
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        brandId, productId, applicationDate, applicationDate)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
