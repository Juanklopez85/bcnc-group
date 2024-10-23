package com.bcnc.prices.service.domain;

public class NotFoundException extends RuntimeException{
  public NotFoundException(String message) {
    super(message);
  }
}
