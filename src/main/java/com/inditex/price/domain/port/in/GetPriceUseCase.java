package com.inditex.price.domain.port.in;

import com.inditex.price.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Use case interface for retrieving the applicable price.
 * <p>
 * This inbound port defines the business operation for finding the most relevant price
 * for a specific brand, product, and application date. It represents the entry point
 * into the domain from a caller such as a service or controller.
 */
public interface GetPriceUseCase {

    /**
     * Retrieves the applicable price for the given brand, product, and application date.
     *
     * @param brandId         the ID of the brand
     * @param productId       the ID of the product
     * @param applicationDate the date and time for which the price is requested
     * @return an {@link Optional} containing the applicable {@link Price}, or empty if none is found
     */
    Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime applicationDate);

}
