package com.javaadash.tc2.core;

import com.javaadash.tc2.core.card.effect.damage.DamageModifier;

public class MockDamageModifier implements DamageModifier {

  private int modifier;

  public MockDamageModifier(int modifier) {
    this.modifier = modifier;
  }

  @Override
  public int getValue(int initialDamage) {
    return modifier;
  }

  @Override
  public String getCardName() {
    return null;
  }

}
