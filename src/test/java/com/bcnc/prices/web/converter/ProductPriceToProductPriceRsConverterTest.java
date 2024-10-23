package com.bcnc.prices.web.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bcnc.prices.service.domain.ProductPrice;
import com.bcnc.prices.web.model.ProductPriceRs;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.extensions.spring.test.ConverterScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductPriceToProductPriceRsConverterTest {
  @Configuration
  @ConverterScan(basePackageClasses = ProductPriceToProductPriceRsConverter.class)
  static class ScanConfiguration {}

  @Autowired
  private ConversionService conversionService;

  @Test
  void givenNullProductPrice_whenConvert_thenReturnNullProductPriceRs() {
    ProductPriceRs productPriceRs = conversionService.convert(null, ProductPriceRs.class);
    assertNull(productPriceRs);
  }

  @Test
  void givenProductPrice_whenConvert_thenReturnProductPriceRs() {
    ProductPrice productPrice = ProductPrice.builder()
        .rateId(1L)
        .productId(2L)
        .chainId(3)
        .price(4.67f)
        .startDate(LocalDateTime.of(2024, 10, 14, 12, 0,0))
        .endDate(LocalDateTime.of(2024, 10, 24, 12, 0,0))
        .build();

    ProductPriceRs productPriceRs = conversionService.convert(productPrice, ProductPriceRs.class);
    assertNotNull(productPriceRs);
    assertAll(
        ()->assertEquals(1L, productPriceRs.getRateId()),
        ()->assertEquals(2L, productPriceRs.getProductId()),
        ()->assertEquals(3L, productPriceRs.getChainId()),
        ()->assertEquals(4.67f, productPriceRs.getPrice()),
        ()->assertEquals(LocalDateTime.of(2024, 10, 14, 12, 0,0), productPriceRs.getStartDate()),
        ()->assertEquals(LocalDateTime.of(2024, 10, 24, 12, 0,0), productPriceRs.getEndDate())
    );
  }
}