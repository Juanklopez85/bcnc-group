package com.bcnc.prices.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(description = "Error response")
public class ErrorRs extends BaseDto {
  @Schema(description = "Error message", example = "Product not valid")
  private String message;

  @Schema(description = "Error code", example = "REQUIRED_FIELD")
  private ErrorCode errorCode;
}
