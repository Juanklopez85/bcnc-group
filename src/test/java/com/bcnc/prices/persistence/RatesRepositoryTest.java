package com.bcnc.prices.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.bcnc.prices.persistence.entity.Rates;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RatesRepositoryTest {

  @Autowired
  private RatesRepository ratesRepository;

  @Test
  void givenBrandIdAndProductId_whenFindRatesByBrandIdAndProductId_thenVerifyResults() {
    List<Rates> ratesList = ratesRepository.findRatesByBrandIdAndProductId(1L, 35455L);

    assertThat(ratesList).isNotEmpty().hasSize(4);
  }

  @Test
  void givenWrongBrandIdAndProductId_whenFindRatesByBrandIdAndProductId_thenVerifyEmptyResult() {
    List<Rates> ratesList = ratesRepository.findRatesByBrandIdAndProductId(12L, 35455L);

    assertThat(ratesList).isEmpty();
  }

  @Test
  void givenBrandIdAndWrongProductId_whenFindRatesByBrandIdAndProductId_thenVerifyEmptyResult() {
    List<Rates> ratesList = ratesRepository.findRatesByBrandIdAndProductId(1L, 35454L);

    assertThat(ratesList).isEmpty();
  }
}