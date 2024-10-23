package com.bcnc.prices.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rates {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name="brand_id")
  private long brandId;

  @Column(name="product_id")
  private long productId;

  private int priority;

  @Column(name="start_date")
  private LocalDateTime startDate;

  @Column(name="end_date")
  private LocalDateTime endDate;

  private float price;
  private String currency;
}
