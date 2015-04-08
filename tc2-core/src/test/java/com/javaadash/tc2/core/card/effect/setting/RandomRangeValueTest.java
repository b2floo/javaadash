package com.javaadash.tc2.core.card.effect.setting;

import junit.framework.TestCase;

public class RandomRangeValueTest extends TestCase {

  public void testGetRandom() {
    RandomRangeValue val = new RandomRangeValue("3");
    assertEquals(new Integer(3), val.getRandom());

    val = new RandomRangeValue("5/8");
    for (int i = 0; i < 10; i++) {
      assertTrue(val.getRandom() >= 5);
      assertTrue(val.getRandom() <= 8);
    }
  }

}
