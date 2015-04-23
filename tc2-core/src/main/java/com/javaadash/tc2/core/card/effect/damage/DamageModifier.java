package com.javaadash.tc2.core.card.effect.damage;

public interface DamageModifier {
  int getValue(int initialDamage);

  String getCardName();
}
