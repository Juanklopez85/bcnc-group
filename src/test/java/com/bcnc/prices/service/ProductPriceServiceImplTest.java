package com.bcnc.prices.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bcnc.prices.persistence.RatesRepository;
import com.bcnc.prices.persistence.entity.Rates;
import com.bcnc.prices.service.domain.ProductPrice;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductPriceServiceImplTest {

  @Mock
  private RatesRepository ratesRepository;
  @Mock
  private ConversionService conversionService;
  @InjectMocks
  private ProductPriceServiceImpl productPriceServiceImpl;

  @ParameterizedTest
  @CsvSource(value = {
      "2020-06-14T10:00:00,35.5",
      "2020-06-14T16:00:00,25.45",
      "2020-06-14T21:00:00,35.5",
      "2020-06-15T10:00:00,30.5",
      "2020-06-16T21:00:00,38.95",
  })
  void GivenDateAndBrandIdAndProductId_WhenGetByDate_ThenReturnProductPriceByDate(LocalDateTime date, float price) {
    // Given
    when(ratesRepository.findRatesByBrandIdAndProductId(1L, 35455L)).thenReturn(List.of(
            Rates.builder().id(1L).brandId(1L).productId(35455L).currency("EUR").priority(0).startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 0)).endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59)).price(35.5f).build(),
            Rates.builder().id(2L).brandId(1L).productId(35455L).currency("EUR").priority(1).startDate(LocalDateTime.of(2020, Month.JUNE, 14, 15, 0, 0)).endDate(LocalDateTime.of(2020, Month.JUNE, 14, 18, 30, 0)).price(25.45f).build(),
            Rates.builder().id(3L).brandId(1L).productId(35455L).currency("EUR").priority(1).startDate(LocalDateTime.of(2020, Month.JUNE, 15, 0, 0, 0)).endDate(LocalDateTime.of(2020, Month.JUNE, 15, 11, 0, 0)).price(30.5f).build(),
            Rates.builder().id(4L).brandId(1L).productId(35455L).currency("EUR").priority(1).startDate(LocalDateTime.of(2020, Month.JUNE, 15, 16, 0, 0)).endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59)).price(38.95f).build()
    ));
    when(conversionService.convert(any(Rates.class), eq(ProductPrice.class))).thenReturn(ProductPrice.builder().build());

    // When
    ProductPrice productPrice = productPriceServiceImpl.getByDate(date, 1L, 35455L);

    // Then
    assertNotNull(productPrice);

    ArgumentCaptor<Rates> argumentCaptor = ArgumentCaptor.forClass(Rates.class);
    verify(conversionService).convert(argumentCaptor.capture(), eq(ProductPrice.class));
    Rates capturedArgument = argumentCaptor.getValue();
    assertEquals(price, capturedArgument.getPrice());
  }
}