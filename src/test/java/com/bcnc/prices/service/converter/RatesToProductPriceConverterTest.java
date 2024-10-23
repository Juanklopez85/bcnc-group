package com.bcnc.prices.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.bcnc.prices.persistence.entity.Rates;
import com.bcnc.prices.service.domain.ProductPrice;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.extensions.spring.test.ConverterScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RatesToProductPriceConverterTest {
  @Configuration
  @ConverterScan(basePackageClasses = RatesToProductPriceConverter.class)
  static class ScanConfiguration {}

  @Autowired
  private ConversionService conversionService;

  @Test
  void givenNullRates_whenConvert_thenReturnNullProductPrice() {
    ProductPrice productPrice = conversionService.convert(null, ProductPrice.class);
    assertNull(productPrice);
  }

  @Test
  void givenRates_whenConvert_thenReturnProductPrice() {
    Rates rates = Rates.builder()
        .id(1L)
        .productId(2L)
        .brandId(3L)
        .priority(4)
        .price(5.67f)
        .currency("EUR")
        .startDate(LocalDateTime.of(2024, 10, 14, 12, 0,0))
        .endDate(LocalDateTime.of(2024, 10, 24, 12, 0,0))
        .build();

    ProductPrice productPrice = conversionService.convert(rates, ProductPrice.class);
    assertNotNull(productPrice);
    assertAll(
        ()->assertEquals(1L, productPrice.getRateId()),
        ()->assertEquals(2L, productPrice.getProductId()),
        ()->assertEquals(3L, productPrice.getChainId()),
        ()->assertEquals(5.67f, productPrice.getPrice()),
        ()->assertEquals(LocalDateTime.of(2024, 10, 14, 12, 0,0), productPrice.getStartDate()),
        ()->assertEquals(LocalDateTime.of(2024, 10, 24, 12, 0,0), productPrice.getEndDate())
    );
  }
}