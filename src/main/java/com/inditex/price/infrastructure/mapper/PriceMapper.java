package com.inditex.price.infrastructure.mapper;


import com.inditex.price.adapter.in.rest.dto.PriceResponse;
import com.inditex.price.adapter.out.persistence.PriceEntity;
import com.inditex.price.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toDomain(PriceEntity entity);

    @Mapping(target = "price", expression = "java(price.getPrice().doubleValue())")
    PriceResponse toResponse(Price price);
}
