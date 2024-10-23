package com.bcnc.prices.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bcnc.prices.service.ProductPriceService;
import com.bcnc.prices.service.domain.NotFoundException;
import com.bcnc.prices.service.domain.ProductPrice;
import com.bcnc.prices.web.model.ProductPriceRq;
import com.bcnc.prices.web.model.ProductPriceRs;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(PriceController.class)
class PriceControllerTest extends ApiTester {

  @MockBean
  private ProductPriceService productPriceService;

  @MockBean
  private FormattingConversionService formattingConversionService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void givenProductPriceRq_whenPricesURI_thenVerifyResponseOK() throws Exception  {
    LocalDateTime now = LocalDateTime.now();
    ProductPrice productPrice = ProductPrice.builder()
        .build();
    when(productPriceService.getByDate(now, 1L, 1L)).thenReturn(productPrice);

    ProductPriceRs productPriceRs = ProductPriceRs.builder()
        .rateId(2L)
        .productId(1L)
        .chainId(1L)
        .startDate(now.minusDays(1L))
        .endDate(now.plusDays(1L))
        .price(10.15f)
        .build();
    when(formattingConversionService.convert(productPrice, ProductPriceRs.class)).thenReturn(productPriceRs);

    mockMvc.perform(
        MockMvcRequestBuilders.post("/prices")
            .content(asJsonString(
                ProductPriceRq.builder()
                    .date(now)
                    .chainId(1L)
                    .productId(1L)
                    .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.chainId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.rateId").value(2L))
        .andExpect(isDateBefore("$.startDate"))
        .andExpect(isDateAfter("$.endDate"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.15f));
  }

  @Test
  void givenProductPriceRqWithoutChainId_whenPricesURI_thenVerifyResponseKO() throws Exception  {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/prices")
                .content(asJsonString(
                    ProductPriceRq.builder()
                        .date(LocalDateTime.now())
                        .productId(1L)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("'chainId' field is required"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("REQUIRED_FIELD"));
  }

  @Test
  void givenProductPriceRqWithoutProductId_whenPricesURI_thenVerifyResponseKO() throws Exception  {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/prices")
                .content(asJsonString(
                    ProductPriceRq.builder()
                        .date(LocalDateTime.now())
                        .chainId(1L)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("'productId' field is required"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("REQUIRED_FIELD"));
  }

  @Test
  void givenProductPriceRqWithoutDate_whenPricesURI_thenVerifyResponseKO() throws Exception  {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/prices")
                .content(asJsonString(
                    ProductPriceRq.builder()
                        .chainId(1L)
                        .productId(1L)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("'date' field is required"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("REQUIRED_FIELD"));
  }

  @Test
  void givenNotFoundException_whenPricesURI_thenVerifyResponseKO() throws Exception  {
    when(productPriceService.getByDate(any(LocalDateTime.class), anyLong(), anyLong())).thenThrow(new NotFoundException("Not found"));
    mockMvc.perform(
            MockMvcRequestBuilders.post("/prices")
                .content(asJsonString(
                    ProductPriceRq.builder()
                        .date(LocalDateTime.now())
                        .chainId(1L)
                        .productId(1L)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not found"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("NOT_FOUND"));
  }

}