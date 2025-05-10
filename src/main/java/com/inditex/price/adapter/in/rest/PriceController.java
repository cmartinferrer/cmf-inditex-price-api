package com.inditex.price.adapter.in.rest;

import com.inditex.price.adapter.in.rest.dto.PriceResponse;
import com.inditex.price.domain.port.in.GetPriceUseCase;
import com.inditex.price.infrastructure.mapper.PriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PriceController implements PricesApi {

    private final GetPriceUseCase service;
    private final PriceMapper mapper;

    @Override
    public ResponseEntity<PriceResponse> getPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        Optional<PriceResponse> response = service.getPrice(brandId.longValue(), productId.longValue(), applicationDate)
                .map(mapper::toResponse);

        return response.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }
}
