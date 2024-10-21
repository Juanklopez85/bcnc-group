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
  void whenFindAll_thenVerifyResults() {
    List<Rates> ratesList = ratesRepository.findAll();

    assertThat(ratesList).isNotEmpty().hasSize(4);
  }
}