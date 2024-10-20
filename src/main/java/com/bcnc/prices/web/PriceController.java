package com.bcnc.prices.web;

import com.bcnc.prices.web.model.ErrorRs;
import com.bcnc.prices.web.model.ProductPriceRq;
import com.bcnc.prices.web.model.ProductPriceRs;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/prices", produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

  @Operation(summary = "Get the price of a product", description = "Given a date, product id and chain id, get the price and rate of a product")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful"),
      @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorRs.class)))
  })
  @PostMapping
  public ResponseEntity<ProductPriceRs> getProductPrice(@Valid @RequestBody ProductPriceRq productPriceRq) {
    return ResponseEntity.ok(ProductPriceRs.builder()
        .productId(productPriceRq.getProductId())
        .chainId(productPriceRq.getChainId())
        .rateId(1)
        .startDate(productPriceRq.getDate().minusDays(1L))
        .endDate(productPriceRq.getDate().plusDays(1L))
        .price(10.15f)
        .build());
  }
}
