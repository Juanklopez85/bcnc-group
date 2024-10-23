package com.bcnc.prices.service.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductPrice {
  private long productId;
  private long chainId;
  private long rateId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private float price;
}
