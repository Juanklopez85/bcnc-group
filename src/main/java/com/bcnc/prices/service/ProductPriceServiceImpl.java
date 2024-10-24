package com.bcnc.prices.service;

import com.bcnc.prices.persistence.RatesRepository;
import com.bcnc.prices.persistence.entity.Rates;
import com.bcnc.prices.service.aspect.annotation.LogTime;
import com.bcnc.prices.service.domain.NotFoundException;
import com.bcnc.prices.service.domain.ProductPrice;
import java.time.LocalDateTime;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

  private final RatesRepository ratesRepository;
  private final ConversionService conversionService;

  @LogTime
  public ProductPrice getByDate(LocalDateTime date, Long brandId, Long productId) {
    Rates rate = ratesRepository.findRatesByBrandIdAndProductId(brandId, productId)
        .stream()
        .filter(rates -> date.isBefore(rates.getEndDate()) && date.isAfter(rates.getStartDate()))
        .max(Comparator.comparingInt(Rates::getPriority))
        .orElseThrow(() -> new NotFoundException("Product not found"));
    return conversionService.convert(rate, ProductPrice.class);
  }
}
