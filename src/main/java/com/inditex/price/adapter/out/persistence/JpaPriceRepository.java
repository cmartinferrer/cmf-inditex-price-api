package com.inditex.price.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for accessing price data from the database.
 * <p>
 * This interface provides methods to query {@link PriceEntity} records,
 * particularly to find those valid for a given brand, product, and application date range.
 */
@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

    /**
     * Finds all {@link PriceEntity} entries that match the given brand and product IDs,
     * and are valid during the specified application date.
     *
     * @param brandId           the ID of the brand
     * @param productId         the ID of the product
     * @param applicationDate1  the lower bound of the date range (inclusive)
     * @param applicationDate2  the upper bound of the date range (inclusive)
     * @return a list of matching {@link PriceEntity} instances
     */
    List<PriceEntity> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long brandId, Long productId, LocalDateTime applicationDate1, LocalDateTime applicationDate2
    );
}
