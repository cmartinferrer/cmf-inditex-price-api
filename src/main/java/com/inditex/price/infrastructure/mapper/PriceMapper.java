package com.inditex.price.infrastructure.mapper;


import com.inditex.price.adapter.out.persistence.PriceEntity;
import com.inditex.price.domain.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    Price toDomain(PriceEntity entity);

}
