package com.bcnc.prices.service;

import com.bcnc.prices.service.domain.ProductPrice;
import java.time.LocalDateTime;

public interface ProductPriceService {

  ProductPrice getByDate(LocalDateTime date, Long brandId, Long productId);
}
