package com.javaadash.tc2.core.card.effect.damage;

import junit.framework.TestCase;

public class DamageModificationEffectTest extends TestCase {

  public void testGetNewDamageValue() {
    DamageModificationEffect effect = new DamageModificationEffect("2", null, null);
    assertEquals(2, effect.getValue(3));
    assertEquals(0, effect.getValue(-1));
  }
}
