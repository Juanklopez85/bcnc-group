package com.bcnc.prices.persistence;

import com.bcnc.prices.persistence.entity.Rates;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {

  List<Rates> findRatesByBrandIdAndProductId(Long brandId, Long productId);
}
