package com.javaadash.tc2.core.card.effect.setting;

import java.util.Random;

public class RandomRangeValue {
  private String description;
  private Integer min;
  private Integer max;

  public RandomRangeValue(String description) {
    this.description = description;
    if (description.indexOf("/") >= 0) {
      this.min = Integer.parseInt(description.substring(0, description.indexOf("/")));
      this.max = Integer.parseInt(description.substring(description.indexOf("/") + 1));
    } else {
      this.min = this.max = Integer.parseInt(description);
    }
  }

  public Integer getRandom() {
    Random rand = new Random();
    return rand.nextInt((max - min) + 1) + min;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "RandomRangeValue [description=" + description + "]";
  }
}
