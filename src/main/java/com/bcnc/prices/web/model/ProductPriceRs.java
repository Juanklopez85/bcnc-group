package com.bcnc.prices.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(description = "Product price response")
public class ProductPriceRs extends BaseDto {
  @Schema(description = "Id of the product", example = "1")
  private long productId;
  @Schema(description = "Id of the chain", example = "1")
  private long chainId;
  @Schema(description = "Id of the rate", example = "1")
  private long rateId;
  @Schema(description = "Starting date of application", example = "2024-10-20T07:37:25.123Z")
  private LocalDateTime startDate;
  @Schema(description = "Ending date of application", example = "2024-10-20T07:37:25.123Z")
  private LocalDateTime endDate;
  @Schema(description = "Final price", example = "15.30")
  private float price;
}
