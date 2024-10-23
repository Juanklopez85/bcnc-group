package com.bcnc.prices.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product price request")
public class ProductPriceRq extends BaseDto {
  @Schema(description = "Date of application", example = "2024-10-20T07:37:25.123Z")
  @NotNull
  private LocalDateTime date;

  @Schema(description = "Id of the product", example = "1")
  @NotNull
  private Long productId;

  @Schema(description = "Id of the chain", example = "1")
  @NotNull
  private Long chainId;
}
