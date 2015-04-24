package com.javaadash.tc2.core.card;

public class RangeValue {
  private Integer min;
  private Integer max;

  public RangeValue(String description) {
    if (description.indexOf("/") >= 0) {
      this.min = Integer.parseInt(description.substring(0, description.indexOf("/")));
      this.max = Integer.parseInt(description.substring(description.indexOf("/") + 1));
    } else {
      this.min = this.max = Integer.parseInt(description);
    }
  }

  public RangeValue(Integer value) {
    this(value, value);
  }

  public RangeValue(Integer min, Integer max) {
    this.min = min;
    this.max = max;
  }

  public Integer getMin() {
    return min;
  }

  public Integer getMax() {
    return max;
  }

  public RangeValue add(Integer modifier) {
    return new RangeValue(min + modifier, max + modifier);
  }

  public RangeValue add(Integer minModifier, Integer maxModifier) {
    int newMin = min + minModifier;
    int newMax = max + maxModifier;
    if (newMin > newMax) {
      newMax = newMin;
    }
    return new RangeValue(newMin, newMax);
  }

  public RangeValue remove(Integer modifier) {
    return new RangeValue(min - modifier, max - modifier);
  }

  public RangeValue remove(Integer minModifier, Integer maxModifier) {
    int newMin = min - minModifier;
    int newMax = max - maxModifier;
    if (newMin > newMax) {
      newMax = newMin;
    }
    return new RangeValue(newMin, newMax);
  }

  public String getDescription() {
    if (min == max) {
      return min.toString();
    } else {
      return min + "/" + max;
    }
  }

  @Override
  public String toString() {
    return getDescription();
  }
}
