package com.bcnc.prices.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bcnc.prices.web.model.ProductPriceRq;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PriceControllerTest extends ApiTester {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void givenProductPriceRq_whenPricesURI_thenVerifyResponseOK() throws Exception  {
    mockMvc.perform(
        MockMvcRequestBuilders.post("/prices")
            .content(asJsonString(
                ProductPriceRq.builder()
                    .date(LocalDateTime.now())
                    .chainId(1)
                    .productId(1)
                    .build()))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.chainId").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.rateId").value(1))
        .andExpect(isDateBefore("$.startDate"))
        .andExpect(isDateAfter("$.endDate"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.15f));
  }

  @Test
  void givenProductPriceRqWithoutChainId_whenPricesURI_thenVerifyResponseKO() throws Exception  {
    mockMvc.perform(
            MockMvcRequestBuilders.post("/prices")
                .content(asJsonString(
                    ProductPriceRq.builder()
                        .date(LocalDateTime.now())
                        .productId(1)
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
                        .chainId(1)
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
                        .chainId(1)
                        .productId(1)
                        .build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("'date' field is required"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("REQUIRED_FIELD"));
  }

}