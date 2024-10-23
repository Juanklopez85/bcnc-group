package com.bcnc.prices.web.converter;

import com.bcnc.prices.service.domain.ProductPrice;
import com.bcnc.prices.web.model.ProductPriceRs;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper
public interface ProductPriceToProductPriceRsConverter extends Converter<ProductPrice, ProductPriceRs> {
}
