package com.inditex.price.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class Price {
    Long brandId;
    Long productId;
    Integer priceList;
    Integer priority;
    LocalDateTime startDate;
    LocalDateTime endDate;
    BigDecimal price;
    String currency;

}
