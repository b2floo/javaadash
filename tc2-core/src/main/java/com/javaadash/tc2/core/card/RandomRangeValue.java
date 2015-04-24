package com.javaadash.tc2.core.card;

import java.util.Random;


public class RandomRangeValue {
  private RangeValue rangeValue;

  public RandomRangeValue(String description) {
    this.rangeValue = new RangeValue(description);
  }

  public RandomRangeValue(RangeValue rangeValue) {
    this.rangeValue = rangeValue;
  }

  public Integer getRandom() {
    Random rand = new Random();
    return rand.nextInt((rangeValue.getMax() - rangeValue.getMin()) + 1) + rangeValue.getMin();
  }

  @Override
  public String toString() {
    return "RandomRangeValue [rangeValue=" + rangeValue + "]";
  }
}
