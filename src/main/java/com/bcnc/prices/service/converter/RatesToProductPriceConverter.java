package com.bcnc.prices.service.converter;

import com.bcnc.prices.persistence.entity.Rates;
import com.bcnc.prices.service.domain.ProductPrice;
import org.springframework.core.convert.converter.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RatesToProductPriceConverter extends Converter<Rates, ProductPrice> {

  @Override
  @Mapping(source = "id", target = "rateId")
  @Mapping(source = "brandId", target = "chainId")
  ProductPrice convert(Rates rates);
}
