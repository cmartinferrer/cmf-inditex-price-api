package com.inditex.price.domain.port.out;

import com.inditex.price.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Outbound port for retrieving prices from a data source.
 * <p>
 * This interface defines the contract for fetching all prices that apply to a given
 * brand, product, and application date.
 */
public interface FindPricePort {

    /**
     * Finds all prices applicable to the specified brand, product, and application date.
     *
     * @param brandId         the ID of the brand
     * @param productId       the ID of the product
     * @param applicationDate the date and time of the price application
     * @return a list of matching {@link Price} domain objects; may be empty if no prices match
     */
    List<Price> findPrices(Long brandId, Long productId, LocalDateTime applicationDate);

}
