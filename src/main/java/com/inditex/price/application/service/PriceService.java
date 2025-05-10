package com.inditex.price.application.service;

import com.inditex.price.domain.model.Price;
import com.inditex.price.domain.port.in.GetPriceUseCase;
import com.inditex.price.domain.port.out.FindPricePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService implements GetPriceUseCase {

    private final FindPricePort repository;

    @Override
    public Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        return repository.findPrices(brandId, productId, applicationDate)
                .stream()
                .max(Comparator.comparing(Price::getPriority));
    }
}
